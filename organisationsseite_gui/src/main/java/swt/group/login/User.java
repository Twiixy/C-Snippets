/*
 * Copyright 2023 Leon Wollandt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package swt.group.login;


/**
 *
 * @author Leon Wollandt
 */
public class User {
    //TODO: Role
    protected String username;
    protected String password;
    protected boolean _isAdmin;
    
    public User(String username, String password, boolean isAdmin)
    {
        this.username = username;
        this.password = password;
        this._isAdmin = isAdmin;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public boolean isAdmin()
    {
        return this._isAdmin;
    }
    
    
    public static User findUser(String username, String password, boolean retNull)
    {
        if(!retNull)
            return new User(username, password, true);
        else
            return null;
    }
}
