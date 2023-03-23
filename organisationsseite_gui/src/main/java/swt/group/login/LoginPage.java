package swt.group.login;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import swt.group.HomePage;
import swt.group.SignInSession;

/**
 *
 * @author di461643
 */
public class LoginPage extends WebPage {

    public LoginPage() {
        add(new loginForm("loginForm"));
        add(new FeedbackPanel("feedback"));
    }

    public final class loginForm extends Form<Void> {

        private final ValueMap properties = new ValueMap();

        public loginForm(final String id) {
            super(id);

            add(new TextField<>("username", new PropertyModel<String>(properties, "username")));
            add(new PasswordTextField("password", new PropertyModel<>(properties, "password")));

            WebMarkupContainer InfoLabel = new WebMarkupContainer("InfoLabel");
            BookmarkablePageLink bookinfo;
            add(bookinfo = new BookmarkablePageLink<>("InfoPage", HomePage.class) {
                @Override
                protected void onConfigure() {
                    super.onConfigure();

                    if (getPageClass() == getPage().getClass()) {
                        InfoLabel.add(new AttributeModifier("class", "bg-red"));

                    }
                }

            });
            bookinfo.add(InfoLabel);
        }

        @Override
        public final void onSubmit() {
            if (signIn(getUsername(), getPassword())) {
                setResponsePage(getApplication().getHomePage());
            } else {
                error("Unknown username/ password");
            }
        }

        private String getPassword() {
            return properties.getString("password");
        }

        /**
         * @return
         */
        private String getUsername() {
            return properties.getString("username");
        }

        private SignInSession getMySession() {
            return (SignInSession) getSession();
        }

        private boolean signIn(String username, String password) {
            if (username != null && password != null) {
                if (getMySession().signIn(username, password)) {
                    return true;
                }
            }
            return false;
        }

    }

}
