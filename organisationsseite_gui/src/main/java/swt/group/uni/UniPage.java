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
package swt.group.uni;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import swt.group.ImagePanel.SwtImage;
import swt.group.NaviPage;
import swt.group.PersonData;
import swt.group.SignInSession;
import swt.group.betrieb.BetriebData;
import swt.group.jdbc.BetriebRepository;
import swt.group.jdbc.PersonRepository;
import swt.group.jdbc.StudiumRepository;

/**
 *
 * @author di461643
 */
@AuthorizeInstantiation({"AZUBI"})
public class UniPage extends WebPage {

    private static ListView<StudiumData> lvStudentdata;
    private List<TextArea> textAreaList;
    boolean isBerteuer;
    PersonData currentPerson;
    //StudiumRepository sp = new StudiumRepository();
    //PersonRepository ps = new PersonRepository();
    List<StudiumData> studentDataList;
    private PersonData selectedAzubi = currentPerson;
    List<List<StudiumData>> studentDataArray;

    public UniPage(final PageParameters parameters) {
        super(parameters);
        isBerteuer = SignInSession.getCurrentUser().rolle.contains("betreuer");
        currentPerson = SignInSession.getCurrentUser();
        List<PersonData> AzubiNameList = new ArrayList();

        add(new SwtImage("swtbild"));
        add(new NaviPage("menu", "UniPage"));
        Form formDropDown = new Form("formDropDown");
        add(formDropDown);

        // TODO Add your page's components here
        List<Integer> semesterList = new ArrayList<Integer>();
        for (int i = 1; i < 7; i++) {
            semesterList.add(i);
        }
        //retrieveStudiumData(int personId)
        if (isBerteuer) {
            AzubiNameList = PersonRepository.retrieveAllAzubisOfAusbilder(Integer.parseInt(currentPerson.id));
            PersonRepository.retrieveAllAzubisOfAusbilder(Integer.parseInt(currentPerson.id));
            studentDataList = StudiumRepository.retrieveStudiumData(Integer.parseInt(currentPerson.id));
        } else {
            AzubiNameList = PersonRepository.retrieveAllAzubisOfAusbilder(Integer.parseInt(currentPerson.id));
            studentDataList = new ArrayList();
            System.out.println("ID=" + currentPerson.id);
            studentDataList = StudiumRepository.retrieveStudiumData(Integer.parseInt(currentPerson.id));

        }
        WebMarkupContainer allcontainer = new WebMarkupContainer("all");
        allcontainer.setVisible(true);
        allcontainer.setOutputMarkupPlaceholderTag(true);

        DropDownChoice<PersonData> DropDownAzubi = new DropDownChoice<PersonData>(
                "DropDownAzubis",
                new PropertyModel(this, "selectedAzubi"),
                AzubiNameList,
                new ChoiceRenderer<PersonData>("vorname")) {
        };
        formDropDown.add(DropDownAzubi);
        if (!isBerteuer) {
            DropDownAzubi.setEnabled(false);
        }
        if (AzubiNameList.size() > 0) {
            selectedAzubi = AzubiNameList.get(0);
        } else {
            selectedAzubi = currentPerson;
        }

        studentDataArray = new ArrayList<List<StudiumData>>();
        for (int i = 0; i < 6; i++) {
            studentDataArray.add(new ArrayList<StudiumData>());
        }

        for (int i = 0; i < studentDataList.size(); i++) {
            StudiumData stmp = studentDataList.get(i);
            studentDataArray.get(stmp.semester - 1).add(stmp);
        }

        ListView<Integer> UniListView = new ListView<Integer>("UniListview", semesterList) {
            @Override
            protected void populateItem(ListItem<Integer> item) {
                Integer tmp = item.getModelObject();

                Form form = new Form<Void>("form");
                WebMarkupContainer lvContainer = new WebMarkupContainer("lvContainer");
                if (studentDataArray.get(item.getIndex()).size() > 0) {
                    lvContainer.setVisible(true);
                } else {
                    lvContainer.setVisible(false);
                }
                lvContainer.setOutputMarkupPlaceholderTag(true);

                AjaxButton addbtn = new AjaxButton("addbtn", form) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        super.onSubmit(target);
                        //studentDataArray.get(tmp - 1).add(
                        //        new StudiumData("ToDo:idGenerieren", "ToDo:PID von aktueller Person", tmp, "", ""));
                        if (!isBerteuer) {
                            StudiumRepository.insertStudiumData(new StudiumData("" + ThreadLocalRandom.current().nextInt(5, 80000 + 1), currentPerson.id, tmp, "", ""));
                        } else {
                            StudiumRepository.insertStudiumData(new StudiumData("" + ThreadLocalRandom.current().nextInt(5, 80000 + 1), selectedAzubi.id, tmp, "", ""));
                        }
                        updateListview();
                        target.add(allcontainer);
                    }
                };
                lvContainer.queue(addbtn);

                AjaxLink al = (new AjaxLink("hide_show_link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        lvContainer.setVisible(!lvContainer.isVisible());
                        target.add(lvContainer);
                    }
                });

                al.queue(new Label("templateName", tmp + ". Semester"));
//                if(studentDataArray.get(tmp-1).isEmpty()){
//                    studentDataArray.get(tmp-1).add(new StudentData("","",tmp,"",""));
//                }

                lvStudentdata = (new ListView<StudiumData>("studentDataLV", studentDataArray.get(tmp - 1)) {
                    @Override
                    protected void populateItem(ListItem<StudiumData> item2) {
                        StudiumData tmp2 = item2.getModelObject();

                        WebMarkupContainer lv2Container = new WebMarkupContainer("lv2Container");
                        lv2Container.setOutputMarkupPlaceholderTag(true);
                        lv2Container.setVisible(true);
                        item2.add(lv2Container);

                        TextArea fachbox = new TextArea<String>("facharea", Model.of(tmp2.fach));
                        TextArea inhaltbox = new TextArea<String>("inhaltarea", Model.of(tmp2.inhalt));
                        lv2Container.queue(fachbox);
                        lv2Container.queue(inhaltbox);

                        AjaxButton deletebtn = new AjaxButton("deletebtn", form) {
                            @Override
                            protected void onSubmit(AjaxRequestTarget target) {
                                super.onSubmit(target);

                                if (!isBerteuer) {
                                    StudiumRepository.deleteStudiumData(Integer.parseInt(tmp2.id));
                                } else {
                                    StudiumRepository.deleteStudiumData(Integer.parseInt(tmp2.id));

                                }
                                updateListview();
                                target.add(allcontainer);
                            }
                        };
                        lv2Container.queue(deletebtn);

                        AjaxButton speicherbtn = new AjaxButton("speicherbtn", form) {
                            @Override
                            protected void onSubmit(AjaxRequestTarget target) {
                                super.onSubmit(target);
                                String a1 = " ";
                                String a2 = " ";
                                if (fachbox.getModelObject() != null) {
                                    a1 = fachbox.getModelObject().toString();
                                }
                                if (inhaltbox.getModelObject() != null) {
                                    a2 = inhaltbox.getModelObject().toString();
                                }

                                if (!isBerteuer) {
                                    StudiumRepository.updateStudiumData(new StudiumData(tmp2.id, currentPerson.id, tmp2.semester, a1, a2));
                                } else {
                                    StudiumRepository.updateStudiumData(new StudiumData(tmp2.id, selectedAzubi.id, tmp2.semester, a1, a2));
                                }
                                updateListview();
                                target.add(allcontainer);
                            }
                        };
                        lv2Container.queue(speicherbtn);

                    }
                });

                lvStudentdata.setOutputMarkupPlaceholderTag(true);
                form.queue(lvStudentdata);
                lvContainer.queue(form);

                item.queue(lvContainer);
                item.queue(al);
            }
        };
        add(UniListView);
        UniListView.setOutputMarkupPlaceholderTag(true);
        allcontainer.add(UniListView);

        AjaxButton ddbtn = new AjaxButton("ddbtn", formDropDown) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);

                System.out.println(".onUpdate() = " + selectedAzubi.vorname);

                updateListview();

                target.add(allcontainer);

            }
        };
        formDropDown.add(ddbtn);
        add(allcontainer);

        if (!isBerteuer) {
            ddbtn.setVisible(false);
        }

    }

    private void updateListview() {
        studentDataList = StudiumRepository.retrieveStudiumData(Integer.parseInt(selectedAzubi.id));
        for (int i = 0; i < studentDataArray.size(); i++) {
            studentDataArray.get(i).clear();
        }
        for (int i = 0; i < studentDataList.size(); i++) {
            StudiumData stmp = studentDataList.get(i);
            studentDataArray.get(stmp.semester - 1).add(stmp);
        }
    }

}
