<%@ page import="com.toro.esb.core.esbpackage.model.ESBPackage" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    ESBPackage esbPackage = (ESBPackage)getServletConfig().getServletContext().getAttribute( "ESB_PACKAGE" );
    String packageName = esbPackage.getName();
%>

<head>
    <meta charset="utf-8">
    <title>TORO Twilio Adapter</title>
    <meta name="author" content="">
    <meta name="description" content="">
    <meta name="keywords" content="">

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/assets/css/uikit/uikit.css">
    <link rel="stylesheet" href="/assets/css/uikit/uikit-components.css">
    <link rel="stylesheet" href="/assets/css/uikit/uikit-docs.css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">


    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700">
    <link rel="stylesheet" href="/assets/css/bootstrap/datetimepicker.css">
    <link rel="stylesheet" href="/assets/css/layout-esb.css">
    <link rel="stylesheet" href="/assets/css/layout-error_404.css">
    <!-- jQuery and jQueryUI -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <!-- globalize for graphs -->
    <script src="//ajax.aspnetcdn.com/ajax/globalize/0.1.1/globalize.min.js"></script>
    <script src="/assets/js/vendor/jquery.flot.js"></script>
    <script src="/assets/js/vendor/uikit-flot.js"></script>
    <script src="/assets/js/plugins/moment.js"></script>
    <!-- Bootstrap Plugins -->
    <script src="/assets/js/bootstrap/bootstrap.js"></script>
    <script src="/assets/js/bootstrap/datetimepicker.min.js"></script>
    <!-- UIKit Plugins -->
    <script src="/assets/js/plugins/prettify.js"></script>
    <script src="/assets/js/plugins/plugin.js"></script>
    <script src="/assets/js/plugins/tableSorter.js"></script>
    <script src="/assets/js/plugins/pagination.js"></script>
    <script src="/assets/js/plugins/sticky-navbar.js"></script>
    <script src="/assets/js/plugins/jquery.tooltipster.min.js"></script>
    <script src="/assets/js/plugins/hoverChart.js"></script>
    <script src="/assets/js/docs.js"></script>
    <script src="/assets/esb/js/esb.js"></script>
</head>
<body style="position: relative;">
<div class="bs-docs-header" id="content" style="background-image: linear-gradient(to bottom, #313131 0%, #4b4b4b 100%);">
    <div class="container" id="top">
        <h1 style="width: 100%">Twilio Adapter</h1>

        <p style="color:#ffffff">Read below on how to write beautiful code that seamlessly integrates the TORO Integrate with Twilio.</p>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-9" role="main">
            <div class="bs-docs-section">
                <h1 id="overview" class="page-header">
                    Overview
                </h1>

                <p>
                    The TORO Twilio adapter is a small <a href="http://groovy.codehaus.org/">Groovy</a> script
                    that allows you to manage your
                    <a href="http://www.mailgun.com/">Twilio</a> account from
                    your own integrations in the TORO Integrate. Once configured, it's literally a 'one-liner', as shown below.
                    <br/>
                    <br/>
                <pre class="prettyprint lang-groovy linenums">'toroio'.twilioSendMessage('+639276907213', '+16693420910', 'Hi there')</pre>
                </p>
            </div>
            <div class="bs-docs-section">
                <h1 id="authenticating" class="page-header">Authenticating</h1>

                <h3 id="getting-oauth-keys">Basic Authentication</h3>

                <p>
                    Twilio Uses Basic authentication in order to securely access Twilio methods. To do this you should save your twilio credentials (APIkey, AuthToken) in the TORO Integrate. Thankfully, this procedure only needs to be executed once.
                </p>

                <h4> Saving the API Keys to TORO Integrate</h4>

                <p>
                    First and foremost, if you're not registered with Twilio, please do so now. Next, upon loggin in to your account click show API credntials at the top of your dashboard to view your credetials.

                    <image src="img/tw1.png" width="100%">
                    <center><strong>Twilio Dashboard</strong></center>

                     The last step is to invoke an Integrate service called <a target="_blank" href="/invoker/get/toro-twilio-adapter/groovy:io.toro.integrate.twilio.TwilioRegister/public%20java.lang.String%20io.toro.integrate.twilio.TwilioRegister.saveConfigData(java.lang.String,java.lang.String,java.lang.String)">saveConfigData</a>. You would need to add the following credentials
                     <ul>
                     <li> <strong>alias</strong> - A unique alias that would be easy to remember, this would handle your API keys</li>
                     <li> <strong>apiKey</strong> - the the Account Sid that is provided by Twilio</li>
                     <li> <strong>apiToken</strong> - the Auth Token provided by Twilio </li>
                     </ul>
                     For Example if you want to send a Twilio SMS:
                     <pre class="prettyprint lang-groovy linenums">'yourAlias'.twilioSendMessage("{To number}", "{From number}", "{message body}")</pre>
                </p>
            </div>
            <div class="bs-docs-section">
                <h1 id="usage" class="page-header">
                    Usage
                </h1>
                The TORO Twilio adapter comes with predefiened methods that is easy to use. TORO Twilio has a method for sending SMS called<code>sendMessage</code> that requires the following parameter:
                <br>
                <ul>
                    <li><strong>body</strong> - your registered mailgun domain</li>
                    <li><strong>from</strong> - Name of the Sender</li>
                    <li><strong>to</strong> - Subject of the mail</li>
                </ul>
                Usage
                <pre class="prettyprint lang-groovy linenums">'alias'.sendMessage({from}, {to}, {body}) // returns Success Message!</pre>
                You can test the service here:
                <ul>
                    <li><a target="_blank"
                           href="/invoker/get/toro-twilio-adapter/groovy:io.toro.integrate.twilio.TwilioAdapter/public%20com.toro.esb.core.api.APIResponse%20io.toro.integrate.twilio.TwilioAdapter.sendMessage(java.lang.String,java.lang.String,java.lang.String,java.lang.String)">sendMesssage</a>
                    </li>
                </ul>

            </div>

        </div>
        <div class="col-md-3">
            <div class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top" role="complementary">
                <ul class="nav bs-docs-sidenav">
                    <li class="active"><a href="#overview">Overview</a></li>
                    <li>
                        <a href="#authenticating">Authenticating</a>
                    </li>
                    <li>
                        <a href="#usage">Usage</a>
                    </li>
                </ul>
                <a class="back-to-top" href="#top">
                    Back to top
                </a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.prettyPrint && prettyPrint();
</script>

</body>
</html>