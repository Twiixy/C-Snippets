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
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import swt.group.HomePage;

/**
 * Simple logout page.
 * 
 * @author Jonathan Locke
 */
public class LogoutPage extends WebPage
{
    /**
     * Constructor
     */
    public LogoutPage()
    {
        getSession().invalidate();
        WebMarkupContainer InfoLabel =new WebMarkupContainer("InfoLabel");
        BookmarkablePageLink bookinfo;
        add(bookinfo= new BookmarkablePageLink<>("InfoPage",HomePage.class){
            @Override
            protected void onConfigure() {
                super.onConfigure(); 
                
                if(getPageClass()==getPage().getClass()){
                   InfoLabel.add(new AttributeModifier("class","bg-red"));
                      
                }
            }
           
        
        });
        bookinfo.add(InfoLabel);
    }
}
