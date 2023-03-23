/*
 * Copyright 2023 Daniel.
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
package swt.group.verwalter;

/**
 *
 * @author Daniel
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import swt.group.ImagePanel.SwtImage;
import swt.group.NaviPage;
import swt.group.PersonData;
import swt.group.jdbc.PersonRepository;
import swt.group.verwalter.addBetreuer.AddBetreuer;
import swt.group.verwalter.addStudent.AddStudent;
import swt.group.verwalter.editRelation.EditRelation;

/**
 *
 * @author di461643
 */
@AuthorizeInstantiation("ADMIN")
public class RelationenView extends WebPage {
    String azubiNamen2;
    String azubiNamen;

    public RelationenView(final PageParameters parameters) {
        super(parameters);

        add(new SwtImage("swtbild"));
        add(new NaviPage("menu", "UniPage"));
        
        Form formAddBetreuer = new Form("formAddBetreuer");
        Form formAddAzubi = new Form("formAddAzubi");
        add(formAddAzubi);
        add(formAddBetreuer);
        
        AjaxButton addBetreuerbtn = new AjaxButton("addBetreuerbtn",formAddBetreuer){
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target); 
                setResponsePage(AddBetreuer.class);
            }
            
        };
        formAddBetreuer.add(addBetreuerbtn);
        
        AjaxButton addStudentbtn = new AjaxButton("addStudentbtn",formAddAzubi){
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target); 
                setResponsePage(AddStudent.class);
            }
            
        };
        formAddAzubi.add(addStudentbtn);
        
//retrieveAllAusbilder
        List<PersonData> ausbilderList = PersonRepository.retrieveAllAusbilder();

        WebMarkupContainer ausbilderContainer = new WebMarkupContainer("ausbilderContainer");
        ausbilderContainer.setVisible(true);
        ausbilderContainer.setOutputMarkupPlaceholderTag(true);
        
        AjaxLink aL1 = (new AjaxLink("hide_show_link_Ausbilder") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        ausbilderContainer.setVisible(!ausbilderContainer.isVisible());
                        target.add(ausbilderContainer);
                    }
                });

                aL1.add(new Label("showAusbilderList","Ausbilderliste ein-/ausblenden"));
                add(aL1);

        ListView ausbilderListview = new ListView<PersonData>("UniListview", ausbilderList) {
            @Override
            protected void populateItem(ListItem<PersonData> item) {
                PersonData tmp = item.getModelObject();

                

                item.add(new Label("ausbilderName", tmp.vorname + " " + tmp.nachname));

                //todo liste von abhängigen azubis bekommen
               ArrayList<PersonData>  xL= PersonRepository.retrieveAllAzubisOfAusbilder(Integer.parseInt(tmp.id));
                azubiNamen = "";
               for(int i=0;i<xL.size();i++){
                   azubiNamen += xL.get(i).vorname+" "+xL.get(i).nachname;
                   if(i<xL.size()-1)
                       azubiNamen+=",";
               }
               if(azubiNamen.length()==0)
                   azubiNamen="keine";
                
                //for(int i=0;i< DataList; i++){azubiNamen+=DataList.get(i)+", ";

                item.add(new Label("azubiNamen", azubiNamen));
                StatelessLink editlink = new StatelessLink("editlink") {
                    @Override
                    public void onClick() {
                        setResponsePage(new EditRelation(parameters,tmp,azubiNamen,1));
                    }

                };
                item.add(editlink);

            }
        };
        ausbilderContainer.add(ausbilderListview);
        add(ausbilderContainer);
        
        
        
        
        List<PersonData> azubilist = PersonRepository.retrieveAllStudents();

        WebMarkupContainer azubiContainer = new WebMarkupContainer("azubiContainer");
        azubiContainer.setVisible(true);
        azubiContainer.setOutputMarkupPlaceholderTag(true);
        
        AjaxLink aL2 = (new AjaxLink("hide_show_link_Azubi") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        azubiContainer.setVisible(!azubiContainer.isVisible());
                        target.add(azubiContainer);
                    }
                });

                aL2.add(new Label("showAzubiList","Azubis ein-/ausblenden"));
                add(aL2);

        ListView azubiListview = new ListView<PersonData>("AListview", azubilist) {
            @Override
            protected void populateItem(ListItem<PersonData> item) {
                PersonData tmp = item.getModelObject();

                

                item.add(new Label("azubiName1", tmp.vorname + " " + tmp.nachname));

                //todo liste von abhängigen azubis bekommen
               ArrayList<PersonData>  xL= PersonRepository.retrieveAllAusbilderOfAzubi(Integer.parseInt(tmp.id));
                azubiNamen2 = "";
               for(int i=0;i<xL.size();i++){
                   azubiNamen2 += xL.get(i).vorname+" "+xL.get(i).nachname;
                   if(i<xL.size()-1)
                       azubiNamen2+=",";
               }
               if(azubiNamen2.length()==0)
                   azubiNamen2="keine";
                
                //for(int i=0;i< DataList; i++){azubiNamen+=DataList.get(i)+", ";

                item.add(new Label("BetreuerNamen1", azubiNamen2));
                Link editlink2 = new Link("editlink2") {
                    @Override
                    public void onClick() {
                        setResponsePage(new EditRelation(parameters,tmp,azubiNamen2,2));
                    }

                };
                item.add(editlink2);

            }
        };
        azubiContainer.add(azubiListview);
        add(azubiContainer);
        

    }

}
