package swt.group;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import swt.group.ImagePanel.SwtImage;
import swt.group.betrieb.BetriebData;
import swt.group.jdbc.InfoseiteRepository;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    PersonData currentPerson;
    static List<InfoseiteData> infoBoxList;
    

    public HomePage(final PageParameters parameters) {
        super(parameters);
          ListView<InfoseiteData> lvInfoSeite;

        currentPerson = SignInSession.getCurrentUser();

        add(new NaviPage("menu", "InfoPage"));
        // TODO Add your page's components here
        add(new SwtImage("swtbild"));
        infoBoxList = InfoseiteRepository.retrieveAll();

        Form form = new Form<Void>("form");
        form.setOutputMarkupPlaceholderTag(true);
        form.setOutputMarkupId(true);
        
        WebMarkupContainer lvContainer = new WebMarkupContainer("lvContainer");
        lvContainer.setVisible(true);
        lvContainer.setOutputMarkupPlaceholderTag(true);
        lvContainer.setOutputMarkupId(true);

        lvInfoSeite = (new ListView<InfoseiteData>("infoSeitelv", infoBoxList) {
            

            @Override
            protected void populateItem(ListItem<InfoseiteData> item) {
                InfoseiteData tmp2 = item.getModelObject();

                TextArea inhaltbox = new TextArea<String>("inhaltarea", Model.of(tmp2.inhalt));
                item.add(inhaltbox);

                AjaxButton deletebtn = new AjaxButton("deletebtn", form) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        super.onSubmit(target);
                        //inhaltbox.setVisible(true);
                        //bs.updateBetriebData(new BetriebData("1000", currentPerson.id, fachbox.getModelObject().toString(), inhaltbox.getModelObject().toString(), kommentarbox.getModelObject().toString(), currentstate));

                        InfoseiteRepository.deleteInfoseiteData(Integer.parseInt(tmp2.id));

                        updateListvie();
                        lvContainer.add(form);
                        target.add(lvContainer);
                    }
                };
                deletebtn.setVisible(false);
                item.queue(deletebtn);
                if (currentPerson!=null&&currentPerson.rolle.contains("admin")) {
                    deletebtn.setVisible(true);
                }

                AjaxButton speicherbtn = new AjaxButton("speicherbtn", form) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        super.onSubmit(target);

                        InfoseiteRepository.updateInfoseiteData(new InfoseiteData(tmp2.id, inhaltbox.getModelObject().toString()));

                        updateListvie();
                        lvContainer.add(form);
                        target.add(lvContainer);
                    }
                };
                speicherbtn.setVisible(false);
                item.queue(speicherbtn);
                if (currentPerson!=null&&currentPerson.rolle.contains("admin")) {
                    speicherbtn.setVisible(true);
                }

            }
        });
        AjaxButton addbtn = new AjaxButton("addbtn", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);

                int indx = InfoseiteRepository.insertInfoseiteData(new InfoseiteData("5", ""));
                //infoBoxList.add(new InfoseiteData("" + indx, ""));
                updateListvie();
                lvContainer.add(form);
                target.add(lvContainer);
            }
        };

        form.add(addbtn);

        lvInfoSeite.setOutputMarkupPlaceholderTag(true);
        lvInfoSeite.setOutputMarkupId(true);
        form.queue(lvInfoSeite);
        lvContainer.add(form);

        add(lvContainer);
         addbtn.setVisible(false);

        if (currentPerson!=null&&currentPerson.rolle.contains("admin")) {
            addbtn.setVisible(true);
        }

    }
    
    private void updateListvie(){
         setResponsePage(HomePage.class);
         
    }

}
