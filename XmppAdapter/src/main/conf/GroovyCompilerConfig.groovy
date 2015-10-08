//this should find the file in the esb jar
URL url = getClass().getClassLoader().getResource( 'ESBGroovyCompilerConfig.groovy' )
if ( url ) {
    System.err << "Found ESB config script, executing... ${url}\n"
    GroovyShell groovyShell = new GroovyShell( getBinding() )
    groovyShell.evaluate( url.toURI() )
    //System.err << 'Found ESB config script, executed successfully\n'
} else
    System.err << 'Could not find ESB GroovyCompilerConfig file\n'
