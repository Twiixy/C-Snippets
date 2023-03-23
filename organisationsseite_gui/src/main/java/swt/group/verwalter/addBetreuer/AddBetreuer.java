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
package swt.group.verwalter.addBetreuer;

/**
 *
 * @author di461643
 */
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import swt.group.NaviPage;
import swt.group.PersonData;
import swt.group.jdbc.PersonRepository;
import swt.group.verwalter.RelationenView;

public class AddBetreuer extends WebPage {
    //PersonRepository pr = new PersonRepository();
	private static final long serialVersionUID = 1L;

	public AddBetreuer(final PageParameters parameters) {
		super(parameters);

                add(new NaviPage("menu","InfoPage"));
                
                FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
          feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
                
                
                Form form =new Form("form"); 
        TextArea TAVorname=new TextArea("taVorname",Model.of(""));
        TextArea TANachname=new TextArea("taNachname",Model.of(""));
        TextArea TaEmail=new TextArea("taEmail",Model.of(""));
        TextArea TABetrieb=new TextArea("taBetrieb",Model.of(""));
         TextArea TAPasswort=new TextArea("taPasswort",Model.of(""));
        
        
        add(form);
        form.add(TAVorname);
        form.add(TANachname);
        form.add(TaEmail);
        form.add(TABetrieb);
        form.add(TAPasswort);
        
        
        Button speicherbtn = new Button("speicherbtn"){
             @Override
            public void onSubmit() {
                super.onSubmit(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                System.out.println(".onSubmit()");
                //PersonData(String id, String vorname, String nachname, String betrieb, String email, String rolle, String passwort)
                PersonData a = new PersonData("1",TAVorname.getModelObject().toString(),TANachname.getModelObject().toString()
                        ,TABetrieb.getModelObject().toString(),TaEmail.getModelObject().toString(),"1",TAPasswort.getModelObject().toString());
                
                PersonRepository.addPerson(a);
                 info("Betreuer hinzugefügt");
            
            }
            
        };
        form.add(speicherbtn);
        
        AjaxButton cancelbtn = new AjaxButton("cancelbtn",form){
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                //0=admin   1=betreuer  2=auszubildener
                
            setResponsePage(RelationenView.class);
            }
           
            
            
        };
        form.add(cancelbtn);

	}
}