/*
 * Copyright 2023 di461643.
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
package swt.group;

/**
 *
 * @author di461643
 */
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import swt.group.betrieb.BetriebPage;
import swt.group.login.LoginPage;
import swt.group.login.LogoutPage;

import swt.group.uni.UniPage;
import swt.group.verwalter.RelationenView;

/**
 * Border component.
 *
 * @author Jonathan Locke
 */
public class NaviPage extends Panel {

    public NaviPage(String id, String pagename) {
        super(id);
        
      
        
        
        
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
        
        
        
        
        
        
        WebMarkupContainer Unilabel =new WebMarkupContainer("UniLabel");
        BookmarkablePageLink bookuni;
        add(bookuni= new BookmarkablePageLink<>("UniPage",UniPage.class){
            @Override
            protected void onConfigure() {
                super.onConfigure(); 
                
                if(getPageClass()==getPage().getClass()){
                   Unilabel.add(new AttributeModifier("class","bg-red"));
                     System.out.println(".onConfigure()"); 
                }
            }
           
        
        });
        bookuni.add(Unilabel);
        
        
        WebMarkupContainer BetriebLabel =new WebMarkupContainer("BetriebLabel");
        BookmarkablePageLink bookbetrieb;
        add(bookbetrieb= new BookmarkablePageLink<>("BetriebPage",BetriebPage.class){
            @Override
            protected void onConfigure() {
                super.onConfigure(); 
                
                if(getPageClass()==getPage().getClass()){
                   BetriebLabel.add(new AttributeModifier("class","bg-red"));
                     System.out.println(".onConfigure()"); 
                }
            }
           
        
        });
        bookbetrieb.add(BetriebLabel);
        
        
        
        WebMarkupContainer VerwalterLabel =new WebMarkupContainer("VerwalterLabel");
        BookmarkablePageLink bookverwalter;
        add(bookverwalter= new BookmarkablePageLink<>("VerwalterPage",RelationenView.class){
            @Override
            protected void onConfigure() {
                super.onConfigure(); 
                
                if(getPageClass()==getPage().getClass()){
                   VerwalterLabel.add(new AttributeModifier("class","bg-red"));
                     System.out.println(".onConfigure()"); 
                }
            }
           
        
        });
        bookverwalter.add(VerwalterLabel);
       
        
        WebMarkupContainer LogLabel =new WebMarkupContainer("LogLabel");
        BookmarkablePageLink booklog;
        if(SignInSession.get().isSignedIn())
        {
            add(booklog= new BookmarkablePageLink<>("LogPage",LogoutPage.class){
            @Override
            protected void onConfigure() {
                super.onConfigure(); 
                
                if(getPageClass()==getPage().getClass()){
                   LogLabel.add(new AttributeModifier("class","bg-red"));
                     System.out.println(".onConfigure()"); 
                }
            }
           
        
        });
        }
        else
        {
            add(booklog= new BookmarkablePageLink<>("LogPage",LoginPage.class){
            @Override
            protected void onConfigure() {
                super.onConfigure(); 
                
                if(getPageClass()==getPage().getClass()){
                   LogLabel.add(new AttributeModifier("class","bg-red"));
                     System.out.println(".onConfigure()"); 
                }
            }
           
        
        });
        }
        
        booklog.add(LogLabel);
        
        VerwalterLabel.setVisible(false);
                Unilabel.setVisible(false);
                BetriebLabel.setVisible(false);
        
          if(SignInSession.get().isSignedIn())
        {
            add(new Label("LoginInfoLabel","Welcome "+SignInSession.getCurrentUser().vorname+" "+SignInSession.getCurrentUser().nachname));
            System.out.println("Rolle = "+SignInSession.getCurrentUser().rolle); 
            if(SignInSession.getCurrentUser().rolle.contains("admin")){
                VerwalterLabel.setVisible(true);
                Unilabel.setVisible(false);
                BetriebLabel.setVisible(false);
            }
            else if(SignInSession.getCurrentUser().rolle.contains("betreuer")){
                VerwalterLabel.setVisible(false);
                Unilabel.setVisible(true);
                BetriebLabel.setVisible(true);
            }
            else {
                VerwalterLabel.setVisible(false);
                Unilabel.setVisible(true);
                BetriebLabel.setVisible(true);
            }
        }
        else
        {
            add(new Label("LoginInfoLabel", "You are not logged in"));
            
        }
        
    }

}
