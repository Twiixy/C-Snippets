package swt.group.verwalter.editRelation;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import swt.group.NaviPage;
import swt.group.PersonData;
import swt.group.SignInSession;
import swt.group.betrieb.BetriebData;
import swt.group.jdbc.BetriebRepository;
import swt.group.jdbc.PersonRepository;
import swt.group.jdbc.StudiumRepository;
import swt.group.verwalter.RelationenView;

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
/**
 *
 * @author Daniel
 */
public class EditRelation extends WebPage {

    PersonData currentPerson;
    private PersonData selectedAzubi = currentPerson;
    List<PersonData> selectetAzubisList;
    List<PersonData> AzubiNameList;

    public EditRelation(final PageParameters parameters, PersonData betreuer, String namen, int rolle) {
        super(parameters);
        add(new NaviPage("menu", "UniPage"));
        currentPerson = SignInSession.getCurrentUser();
        selectetAzubisList = PersonRepository.retrieveAllAzubisOfAusbilder(Integer.parseInt(betreuer.id));
        if (selectetAzubisList == null) {
            selectetAzubisList = new ArrayList<PersonData>();
        }
        ArrayList<PersonData> xtmp= PersonRepository.retrieveAllAzubisWithoutAusbilder();
        String daten="";
        for(int i=0;i<xtmp.size();i++){
            
            daten+=xtmp.get(i).vorname+" "+xtmp.get(i).nachname;
            if(i!=xtmp.size()-1)
                daten+=",";
        }
        if(daten.length()==0)
            daten="keine";
        add(new Label("StudentenOhneBetreuerLabel",daten));
        
        Label l1=new Label("zugStu",daten);
        l1.setVisible(false);

          FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
          feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        Form form = new Form("form");
        TextArea TAVorname = new TextArea("taVorname", Model.of(betreuer.vorname));
        TextArea TANachname = new TextArea("taNachname", Model.of(betreuer.nachname));
        TextArea TaEmail = new TextArea("taEmail", Model.of(betreuer.email));
        TextArea TARolle = new TextArea("taRolle", Model.of(betreuer.betrieb));
        TextArea TAPW = new TextArea("taPW", Model.of(betreuer.passwort));
        add(form);
        form.add(TAVorname);
        form.add(TANachname);
        form.add(TaEmail);
        form.add(TARolle);
        form.add(TAPW);
        WebMarkupContainer allcontainer = new WebMarkupContainer("all");
        allcontainer.setVisible(true);
        allcontainer.setOutputMarkupPlaceholderTag(true);
        add(allcontainer);

        Form formDropDown = new Form("formDropDown");
        add(formDropDown);
      AzubiNameList = new ArrayList();
        AzubiNameList = PersonRepository.retrieveAllStudents();

        DropDownChoice<PersonData> DropDownAzubi = new DropDownChoice<PersonData>(
                "DropDownAzubis",
                new PropertyModel(this, "selectedAzubi"),
                AzubiNameList,
                new ChoiceRenderer<PersonData>("vorname")) {
        };
        DropDownAzubi.setOutputMarkupId(true);
        DropDownAzubi.setOutputMarkupPlaceholderTag(true);
        formDropDown.add(DropDownAzubi);

        AjaxButton speicherbtn = new AjaxButton("speicherbtn3", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit();
                System.out.println("gespeichert und so");

                PersonRepository.updatePerson(new PersonData(betreuer.id, TAVorname.getModelObject().toString(), TANachname.getModelObject().toString(),
                        TARolle.getModelObject().toString(), TaEmail.getModelObject().toString(), "" + rolle,
                        TAPW.getModelObject().toString()));
                success("Person wurde hinzugefügt");
                 target.add(feedbackPanel);

            }
        };
        form.add(speicherbtn);

        AjaxButton cancelbtn = new AjaxButton("cancelbtn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit();
                setResponsePage(RelationenView.class);
            }
        };
        form.add(cancelbtn);

        AjaxButton deletebtn = new AjaxButton("deletebtn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit();
                PersonRepository.deletePerson(Integer.parseInt(betreuer.id));
                 info("Person wurde gelöscht");
                 target.add(feedbackPanel);
                
            }

        };
        form.add(deletebtn);

        //------------------------------------------------------
        AjaxButton ddbtn = new AjaxButton("ddbtn", formDropDown) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);

                selectetAzubisList.add(selectedAzubi);
                AzubiNameList.remove(selectedAzubi);
                

                target.add(allcontainer);
                 target.add(DropDownAzubi);
                 target.add(feedbackPanel);

            }
        };
        formDropDown.add(ddbtn);
        
         AjaxButton savedropdown = new AjaxButton("savedropdown", formDropDown) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                PersonRepository.deleteAllRelationenOfBetreuer(Integer.parseInt(betreuer.id));
                
                //addAusbilderAndAzubi(int ausbilderId, int azubiId)
                for(int i=0;i<selectetAzubisList.size();i++){
                    PersonRepository.addAusbilderAndAzubi(Integer.parseInt(betreuer.id), Integer.parseInt(selectetAzubisList.get(i).id));
                }
                success("Person wurde hinzugefügt");
                 target.add(feedbackPanel);

               // selectetAzubisList;
                

            }
        };
        formDropDown.add(savedropdown);
        
        

        ListView ausbilderListview = new ListView<PersonData>("betreutlv", selectetAzubisList) {
            @Override
            protected void populateItem(ListItem<PersonData> item) {
                PersonData tmp = item.getModelObject();

                item.add(new Label("namelabel", tmp.vorname + " " + tmp.nachname));
                item.add(new Label("betrieblabel", tmp.betrieb));

                AjaxButton deletebtn = new AjaxButton("deletebtn", form) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        super.onSubmit(target);
                        selectetAzubisList.remove(item.getIndex());
                        AzubiNameList.add(tmp);
                        target.add(allcontainer);
                        target.add(DropDownAzubi);
                    }
                };
                item.add(deletebtn);

            }
        };
        ausbilderListview.setOutputMarkupId(true);
        ausbilderListview.setOutputMarkupPlaceholderTag(true);
        allcontainer.add(ausbilderListview);
        formDropDown.add(l1);
        if(rolle == 2){
            DropDownAzubi.setVisible(false);
            allcontainer.setVisible(false);
            ddbtn.setVisible(false);
            savedropdown.setVisible(false);
            l1.setVisible(false);
        }
        

    }

}
