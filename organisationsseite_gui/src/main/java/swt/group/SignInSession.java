package swt.group;

import java.util.ArrayList;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import swt.group.jdbc.PersonRepository;
import swt.group.login.Roles2;


/**
 * Authenticated session subclass. Note that it is derived from AuthenticatedWebSession which is
 * defined in the auth-role module.
 * 
 * @author Jonathan Locke
 */
public class SignInSession extends AuthenticatedWebSession
{
    private static PersonData _user = null;
    
    
    /**
     * Construct.
     * 
     * @param request
     *            The current request object
     */
    public SignInSession(Request request)
    {
        super(request);
    }

    @Override
    public boolean authenticate(final String username, final String password)
    {
        ArrayList<PersonData> search = PersonRepository.retrievePersonDataWithUsername(username);
        
        if(search.isEmpty())
            return false;
        if(PersonRepository.retrievePasswordForUser(username).compareTo(password) != 0)
            return false;
        
        SignInSession._user = search.get(0);
        return true;
            
        //return USERNAME_PASSWORD.equals(username) && USERNAME_PASSWORD.equals(password);
    }
    
    public static PersonData getCurrentUser()
    {
        return SignInSession._user;
    }
    

    @Override
    public Roles getRoles()
    {
        if (isSignedIn())
        {
            if(_user.rolle.compareTo("admin") == 0)
            {
                String[] arr = {Roles2.ADMIN, Roles2.AZUBI, Roles2.BETREUER, Roles2.OTHER};
                return new Roles2(arr);
            }
            else if(_user.rolle.compareTo("betreuer") == 0)
            {
                String[] arr = {Roles2.AZUBI, Roles2.BETREUER, Roles2.OTHER};
                return new Roles2(arr);
            } 
            else if(_user.rolle.compareTo("azubi") == 0)
            {
                String[] arr = {Roles2.AZUBI, Roles2.OTHER};
                return new Roles2(arr);
            } 
            else
            {
                return new Roles(Roles2.OTHER);
            }
        }
        return null;
    }
}