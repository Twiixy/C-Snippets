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

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

/**
 *
 * @author Leon Wollandt
 */
public class Roles2 extends Roles{
    
    public Roles2(String str)
    {
        super(str);
    }
    
    public Roles2(String[] str)
    {
        super(str);
    }
    
    public static final String AZUBI = "AZUBI";
    public static final String BETREUER = "BETREUER";
    public static final String OTHER = "OTHER";
    
}
