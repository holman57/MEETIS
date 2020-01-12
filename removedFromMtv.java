  try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar MeetisTwitter.jar"});
            } catch (Exception ignore) {
            }
            try {
                Robot r = new Robot();
                r.mouseMove(1000, 192);
                r.mousePress(InputEvent.BUTTON1_MASK);
                r.mouseRelease(InputEvent.BUTTON1_MASK);
                TimeUnit.SECONDS.sleep(3);
                r.mouseMove(1008, 670);
                r.mousePress(InputEvent.BUTTON1_MASK);
                r.mouseRelease(InputEvent.BUTTON1_MASK);
                TimeUnit.SECONDS.sleep(1);
                r.mousePress(InputEvent.BUTTON1_MASK);
                r.mouseRelease(InputEvent.BUTTON1_MASK);
                TimeUnit.SECONDS.sleep(1);
                r.mousePress(InputEvent.BUTTON1_MASK);
                r.mouseRelease(InputEvent.BUTTON1_MASK);
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception ignore){
            }
            // top find profile icon scan (x :450 y:285) -> (x:450 y:388)
            // bottom find profile icon scan (x:450 y:522) -> (x:450 y:650)
            //
            // scrollbar VERT top(x:1008 y:183) bottom(x:1008 y:695)
            //                                  large down(x:1108 y:687)
            // scrollbar HORZ left(x:206 y:712) right(x:993 y:712)
            // PROFILE ICON scan top(x:450 y:285) bottom(x:450 y:332)
            do {
                try {
                    pause();
                    Robot r = new Robot();
                    takeScreenShot();
                    if (checkIfPixleWhite(450, 315)){
                        pause();
                        r.mouseMove(1008, 695); // Up /\
                        r.mousePress(InputEvent.BUTTON1_MASK);
                        r.mouseRelease(InputEvent.BUTTON1_MASK);
                    } else {
                         if (checkIfPixleWhite(433, 345)) {
                               pause();
                               r.mouseMove(1008, 183); // Down \/
                               r.mousePress(InputEvent.BUTTON1_MASK);
                               r.mouseRelease(InputEvent.BUTTON1_MASK);
                               takeScreenShot();
                        }
                        TimeUnit.SECONDS.sleep(15);
                        r.mouseMove(1008, 680);
                        r.mousePress(InputEvent.BUTTON1_MASK);
                        r.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                } catch (Exception ignore){
                }
            } while (true);