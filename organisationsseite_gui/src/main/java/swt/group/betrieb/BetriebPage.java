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
package swt.group.betrieb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import swt.group.ImagePanel.SwtImage;
import swt.group.NaviPage;
import swt.group.PersonData;
import swt.group.SignInSession;
import swt.group.jdbc.BetriebRepository;
import swt.group.jdbc.PersonRepository;

/**
 *
 * @author di461643
 */
@AuthorizeInstantiation("AZUBI")
public class BetriebPage extends WebPage {

    private ListView<BetriebData> lvBetriebsdata;
    private static List<TextArea> abgeschlosseneAufgabenTAList = new ArrayList<TextArea>();
    private static List<TextArea> offeneAufgabenTAList = new ArrayList<TextArea>();
    boolean currentstate = true;
    // PersonRepository ps = new PersonRepository();
    // BetriebRepository bs = new BetriebRepository();
    PersonData currentPerson;
    private PersonData selectedAzubi = currentPerson;
    private PersonData modelselectedAzubi = selectedAzubi;
    boolean isBerteuer;
    List<BetriebData> betriebDataList;
    //WebMarkupContainer lvContainer;
    private WebMarkupContainer allcontainer = new WebMarkupContainer("all");
    private ListView<String> BetriebListView;
    List<List<BetriebData>> betriebDataArray;

    public BetriebPage(final PageParameters parameters) {
        super(parameters);
        allcontainer.setOutputMarkupId(true);
        allcontainer.setOutputMarkupPlaceholderTag(true);

        List<PersonData> AzubiNameList = new ArrayList();
        currentPerson = SignInSession.getCurrentUser();

        isBerteuer = SignInSession.getCurrentUser().rolle.contains("betreuer");

        add(new SwtImage("swtbild"));
        add(new NaviPage("menu", "UniPage"));

        Form formDropDown = new Form("formDropDown");
        add(formDropDown);
        // TODO Add your page's components here

        List<String> betriebHeaderList = new ArrayList<String>();
        betriebHeaderList.add("Abgeschlossene Aufgaben");
        betriebHeaderList.add("Offene Aufgaben");

        betriebDataList = new ArrayList<BetriebData>();

        if (isBerteuer) {

            AzubiNameList = PersonRepository.retrieveAllAzubisOfAusbilder(Integer.parseInt(currentPerson.id));
            selectedAzubi = AzubiNameList.get(0);
            betriebDataList = BetriebRepository.retrieveBetriebData(Integer.parseInt(selectedAzubi.id));
            System.out.println("size der list = " + AzubiNameList.size());

        } else {
            //insertBetriebData(BetriebData betriebData)
            betriebDataList = BetriebRepository.retrieveBetriebData(Integer.parseInt(currentPerson.id));

        }

        //https://nightlies.apache.org/wicket/apidocs/8.x/index.html?org/apache/wicket/markup/html/form/ChoiceRenderer.html
        DropDownChoice<PersonData> DropDownAzubi = new DropDownChoice<PersonData>(
                "DropDownAzubis",
                new PropertyModel(this, "selectedAzubi"),
                AzubiNameList,
                new ChoiceRenderer<PersonData>("vorname")) {
        };

        betriebDataArray = new ArrayList<List<BetriebData>>();


        DropDownAzubi.setNullValid(false);
        DropDownAzubi.setVisible(true);//todo: visable aus wenn azubi sicht
        formDropDown.add(DropDownAzubi);

        for (int i = 0; i < 2; i++) {
            betriebDataArray.add(new ArrayList<BetriebData>());
        }
        for (int i = 0; i < betriebDataList.size(); i++) {
            BetriebData btmp = betriebDataList.get(i);
            if (btmp.istAbgeschlossen) {
                betriebDataArray.get(0).add(btmp);
            } else {
                betriebDataArray.get(1).add(btmp);
            }
        }
        if (!isBerteuer) {
            DropDownAzubi.setEnabled(false);
        }
        if (AzubiNameList.size() > 0) {
            selectedAzubi = AzubiNameList.get(0);
        } else {
            selectedAzubi = currentPerson;
        }

        allcontainer.setOutputMarkupPlaceholderTag(true);
        add(allcontainer);

        BetriebListView = new ListView<String>("UniListview", betriebHeaderList) {
            @Override
            protected void populateItem(ListItem<String> item) {
                String tmp = item.getModelObject();

                currentstate = true;
                if (item.getIndex() == 0) {
                    currentstate = false;
                }

                Form form = new Form<Void>("form");
                WebMarkupContainer lvContainer = new WebMarkupContainer("lvContainer");
                lvContainer.setVisible(true);
                lvContainer.setOutputMarkupPlaceholderTag(true);
                lvContainer.setOutputMarkupId(true);

                AjaxButton addbtn = new AjaxButton("addbtn", form) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        super.onSubmit(target);

                        if (!isBerteuer) {
                            BetriebRepository.insertBetriebData(new BetriebData("1000", currentPerson.id, "", "", "", currentstate));
                            // betriebDataArray.get(item.getIndex()).add(new BetriebData("10000", currentPerson.id, "", "", "", currentstate));
                        } else {
                            BetriebRepository.insertBetriebData(new BetriebData("1000", selectedAzubi.id, "", "", "", currentstate));
                            // betriebDataArray.get(item.getIndex()).add(new BetriebData("1000", selectedAzubi.id, "", "", "", currentstate));
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

                al.queue(new Label("aufgabenstatus", tmp));

                lvBetriebsdata = (new ListView<BetriebData>("betriebsDataLV", betriebDataArray.get(item.getIndex())) {
                    @Override
                    protected void populateItem(ListItem<BetriebData> item2) {
                        BetriebData tmp2 = item2.getModelObject();

                        WebMarkupContainer lv2Container = new WebMarkupContainer("lv2Container");
                        lv2Container.setOutputMarkupPlaceholderTag(true);
                        item2.add(lv2Container);

                        TextArea fachbox = new TextArea<String>("titelarea", Model.of(tmp2.titel));
                        TextArea inhaltbox = new TextArea<String>("beschreibungarea", Model.of(tmp2.beschreibung));
                        TextArea kommentarbox = new TextArea<String>("kommentararea", Model.of(tmp2.kommentar));
                        lv2Container.queue(fachbox);
                        lv2Container.queue(inhaltbox);
                        lv2Container.queue(kommentarbox);

                        AjaxButton deletebtn = new AjaxButton("deletebtn", form) {
                            @Override
                            protected void onSubmit(AjaxRequestTarget target) {
                                super.onSubmit(target);
                                fachbox.setVisible(false);
                                inhaltbox.setVisible(false);
                                kommentarbox.setVisible(false);
                                lv2Container.setVisible(false);
                                //bs.updateBetriebData(new BetriebData("1000", currentPerson.id, fachbox.getModelObject().toString(), inhaltbox.getModelObject().toString(), kommentarbox.getModelObject().toString(), currentstate));

                                BetriebRepository.deleteBetriebData(Integer.parseInt(tmp2.id));

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
                                    String a3 = " ";
                                    if (fachbox.getModelObject()!=null) {
                                        a1 = fachbox.getModelObject().toString();
                                    }
                                    if (inhaltbox.getModelObject() != null) {
                                        a2 = inhaltbox.getModelObject().toString();
                                    }
                                    if (kommentarbox.getModelObject() != null) {
                                        a3 = kommentarbox.getModelObject().toString();
                                    }
                                if (!isBerteuer) {
                                    BetriebRepository.updateBetriebData(new BetriebData(tmp2.id, currentPerson.id, a1, a2, a3, currentstate));
                                } else {
                                    BetriebRepository.updateBetriebData(new BetriebData(tmp2.id, selectedAzubi.id, a1, a2, a3, currentstate));
                                }

                                updateListview();
                                target.add(allcontainer);
                            }
                        };
                        lv2Container.queue(speicherbtn);

                        AjaxButton stateswitchbtn = new AjaxButton("stateswitchbtn", form) {
                            @Override
                            protected void onSubmit(AjaxRequestTarget target) {
                                super.onSubmit(target);

                                if (tmp2.istAbgeschlossen) {
                                    betriebDataArray.get(0).remove(tmp2);
                                    tmp2.istAbgeschlossen = !tmp2.istAbgeschlossen;
                                    if (!isBerteuer) {
                                        BetriebRepository.updateBetriebData(new BetriebData(tmp2.id, currentPerson.id, tmp2.titel, tmp2.beschreibung, tmp2.kommentar, tmp2.istAbgeschlossen));
                                    } else {
                                        BetriebRepository.updateBetriebData(new BetriebData(tmp2.id, selectedAzubi.id, tmp2.titel, tmp2.beschreibung, tmp2.kommentar, tmp2.istAbgeschlossen));
                                    }
                                    betriebDataArray.get(1).add(tmp2);

                                } else {
                                    betriebDataArray.get(1).remove(tmp2);
                                    tmp2.istAbgeschlossen = !tmp2.istAbgeschlossen;
                                    if (!isBerteuer) {
                                        BetriebRepository.updateBetriebData(new BetriebData(tmp2.id, currentPerson.id, tmp2.titel, tmp2.beschreibung, tmp2.kommentar, tmp2.istAbgeschlossen));
                                    } else {
                                        BetriebRepository.updateBetriebData(new BetriebData(tmp2.id, selectedAzubi.id, tmp2.titel, tmp2.beschreibung, tmp2.kommentar, tmp2.istAbgeschlossen));
                                    }
                                    betriebDataArray.get(0).add(tmp2);

                                }
                                updateListview();
                                target.add(allcontainer);
                            }
                        };
                        lv2Container.queue(stateswitchbtn);

                    }
                });

                lvBetriebsdata.setOutputMarkupPlaceholderTag(true);
                form.queue(lvBetriebsdata);
                lvContainer.queue(form);

                item.queue(lvContainer);
                item.queue(al);
            }
        };
        allcontainer.add(BetriebListView);
        BetriebListView.setOutputMarkupPlaceholderTag(true);
        BetriebListView.setOutputMarkupId(true);
        BetriebListView.setOutputMarkupPlaceholderTag(true);

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

        if (!isBerteuer) {
            ddbtn.setVisible(false);
        }

    }

    private void updateListview() {
        betriebDataList = BetriebRepository.retrieveBetriebData(Integer.parseInt(selectedAzubi.id));
        betriebDataArray.clear();
        //betriebHeaderList.clear();
        for (int i = 0; i < 2; i++) {
            betriebDataArray.add(new ArrayList<BetriebData>());
        }

        for (int i = 0; i < betriebDataList.size(); i++) {
            BetriebData btmp = betriebDataList.get(i);

            if (btmp.istAbgeschlossen) {
                betriebDataArray.get(0).add(btmp);
            } else {
                betriebDataArray.get(1).add(btmp);
            }
        }
    }

}
