package swt.group;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.markup.html.WebPage;
import swt.group.login.LoginPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see swt.group.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {

    
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage()
    {
        return HomePage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
    {
        return SignInSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass()
    {
        return LoginPage.class;
    }
    
    
    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        // needed for the styling used by the quickstart
        getCspSettings().blocking()
                .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
                .add(CSPDirective.STYLE_SRC, "https://fonts.googleapis.com/css")
                .add(CSPDirective.FONT_SRC, "https://fonts.gstatic.com");

        // add your configuration here
    }

    

}
