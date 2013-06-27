class QrCodeGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "QRCode Plugin" // Headline display name of the plugin
    def author = "Ly Tung"
    def authorEmail = "tyanhly@gmail.com"
    def description = '''\
The QRCode Plugin allows your Grails application create QRCode image \
which you can put your logo to this image. 
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/qrcode"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "KiSS Concept", url: "http://kiss-concept.com" ]

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Tung Ly", email: "tung.ly@kiss-concept.com" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GRAILS-10200" ]

    // Online location of the plugin's browseable source code.
    
    def scm = [ url: "https://github.com/tyanhly/QRCode.git" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
