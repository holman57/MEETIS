import discord
import torch
import time
from secret.discord_secrets import DISCORD_TOKEN, TEST_GUILD
from transformers import AutoTokenizer, AutoModelForCausalLM

tokenizer = AutoTokenizer.from_pretrained("gpt2")
model = AutoModelForCausalLM.from_pretrained("gpt2")
bad_words_ids = [tokenizer(bad_word).input_ids for bad_word in ["idiot", "stupid", "shut up"]]
client = discord.Client()
active = []


def generate(input_context):
    if 'meetis' in input_context:
        input_context = input_context.replace('meetis', '')
    if 'hey' in input_context or 'hello' in input_context or 'sup ' in input_context or "what's up" in input_context:
        if 'they' not in input_context:
            input_context = 'The AI wanted to say hello so it said, '
    input_ids = tokenizer(input_context, return_tensors="pt").input_ids
    outputs = model.generate(input_ids=input_ids,
                             min_length=3,
                             max_length=100,
                             do_sample=True,
                             num_beams=4,
                             top_k=50,
                             top_p=0.95,
                             no_repeat_ngram_size=2,
                             early_stopping=True,
                             temperature=0.8,
                             bad_words_ids=bad_words_ids)
    response = tokenizer.decode(outputs[0], skip_special_tokens=True)
    print(response)
    response = response[len(input_context):]
    if '\n' in response:
        for line in response.split('\n'):
            if len(line) < 6:
                continue
            response = line
            break
    return response


@client.event
async def on_ready():
    for guild in client.guilds:
        if guild.name == TEST_GUILD:
            break
    print(f'{client.user} is connected to the following guild: {guild.name} (id: {guild.id})')


@client.event
async def on_message(message):
    print(f"{message.author} sent a message")
    print(message.content)
    message_time = time.time()
    if message.author == client.user:
        return
    if 'meetis' in message.content.lower():
        for act in active:
            if act[0] == message.author:
                act[1] = time.time()
                await message.channel.send(generate(message.content))
                return
        active.append([message.author, time.time()])
    if len(active) == 0:
        return
    for act in active:
        if act[0] == message.author:
            elapsed = message_time - act[1]
            print(f"Last message from {message.author} sent: {elapsed:.2f}s ago")
            if elapsed > 30:
                active.remove(act)
            else:
                act[1] = time.time()
                await message.channel.send(generate(message.content))


client.run(DISCORD_TOKEN)
