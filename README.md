Erika  [![Build Status](https://travis-ci.org/kingsleyh/erika.png?branch=master)](https://travis-ci.org/kingsleyh/erika) [ ![Download](https://api.bintray.com/packages/kingsleyh/repo/erika/images/download.svg) ](https://bintray.com/kingsleyh/repo/erika/_latestVersion)
==========

Erika was originally designed as a pure Scala wrapper around the phantomJs GhostDriver API. However she has been expanded to cope with:

* PhantomJs (Ghostdriver)
* ChromeDriver
* Wires (Firefox) - currently not working
* BrowserStack

When writing Erika I have attempted to make the functions as robust as possible. For example instead of relying on a drivers ability to findElements - Erika
continually makes the findElements call until a timeout has been reached or success. There are some functions for blowing up if elements are not found 
and there are some functions which don't blow up and return an option instead.

There are also wrappers around common types of elements e.g. TextInput which add another layer or robustness around getting, setting and clearing values.

### Getting Erika

Add a resolver to my bintray repo and then add a library dependency specifying the version. You can see the latest version on the download label next to the build 
status at the top of this page.

(I also push to the sonatype oss maven repo occasionally or if requested via an Issue.)

     resolvers += "Kingsley Hendrickse's repo" at "http://dl.bintray.com/kingsleyh/repo/"

     libraryDependencies += "erika" %% "erika_2.11" % "<latest version>"
     
### Getting Started
     
Have a look at the [Documentation](https://rawgit.com/kingsleyh/erika/master/docs/documentation.html)

Once you have Erika you must choose the kind of session you want to run e.g chrome, phantomjs. Below are some examples of using these.

#### ChromeSession

    ChromeSession(
      pathToChromeDriver = "/path/to/binary/chromedriver"
    )(session => {
    
      session.visitUrl("http://localhost:8080/app/#login")
    
      session
        .waitFor(By.className("username")).toTextInput.setValue("email")
        .waitFor(By.className("password")).toTextInput.setValue("password")
        .waitFor(By.className("login")).toButton.click()
    
      val element: WebElement = session.waitForClass("welcome-page")
      println(element.getText)
    
    })
            
The ChromeSession object has a few things you can set - the most important being the path to the chromedriver binary. You can also set 
parameters to pass to the chromedriver binary via the ChromeOptions argument of ChromeSession. 

### PhantomJsSession

    PhantomJsSession(
      pathToPhantom = "/path/to/binary/phantomjs",
      phantomJsOptions = PhantomJsOptions().setIgnoreSslError(true)
                                           .setSslProtocol(SSLProtocol.ANY)
                                           .setWebSecurity(false)
    )(session => {
    
      session.visitUrl("http://localhost:8080/app/#login")
    
      session
        .waitFor(By.className("username")).toTextInput.setValue("email")
        .waitFor(By.className("password")).toTextInput.setValue("password")
        .waitFor(By.className("login")).toButton.click()
    
      val element: WebElement = session.waitForClass("welcome-page")
      println(element.getText)
    
    })
        
The PhantomJsSession object need to know the path to the phantomjs binary. You also set parameter to pass to the phantomjs binary using the PhantomJsOptions
object. In the example above it shows a common configuration for testing against HTTPS websites.

### BrowserStackSession

    val capabilities = Capabilities(
        browserStack = Some(BrowserStackCapabilities(
        name = Some("Kingsley"),
        browser = Some("Chrome"),
        browserVersion = Some("50"),
        os = Some("Windows"),
        osVersion = Some("10"),
        resolution = Some("1920x1080"),
        project = Some("MYPROJECT [KH]"),
        browserStackLocal = Some(false),
        browserStackDebug = Some(true))),
        nativeEvents = true)
        
    BrowserStackSession(
      basicAuth = Some(BasicAuth("browserstack-username", "browserstack-api-key")),
          desiredCapabilities = capabilities
    )(session => {
    
      session.visitUrl("http://localhost:8080/app/#login")
    
      session
        .waitFor(By.className("username")).toTextInput.setValue("email")
        .waitFor(By.className("password")).toTextInput.setValue("password")
        .waitFor(By.className("login")).toButton.click()
    
      val element: WebElement = session.waitForClass("welcome-page")
      println(element.getText)
    
    })
        
When testing against Browserstack you need to connect with your auth credentials - username and api key. You also need to supply the desired 
capabilities you want - e.g. the platform (Windows), platform version (10), the browser (Chrome) and browser version (50).


        