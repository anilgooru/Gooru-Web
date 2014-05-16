package org.ednovo.gooru.client.uc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class TocCollectionEndView_TocCollectionEndViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, org.ednovo.gooru.client.uc.TocCollectionEndView>, org.ednovo.gooru.client.uc.TocCollectionEndView.TocCollectionEndViewUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("")
    SafeHtml html1();
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final org.ednovo.gooru.client.uc.TocCollectionEndView owner) {


    return new Widgets(owner).get_endContainer();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final org.ednovo.gooru.client.uc.TocCollectionEndView owner;


    final com.google.gwt.event.dom.client.ErrorHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.google.gwt.event.dom.client.ErrorHandler() {
      public void onError(com.google.gwt.event.dom.client.ErrorEvent event) {
        owner.onErrorImageLoad(event);
      }
    };

    public Widgets(final org.ednovo.gooru.client.uc.TocCollectionEndView owner) {
      this.owner = owner;
      build_playerStyle();  // generated css resource must be always created. Type: GENERATED_CSS. Precedence: 1
    }

    SafeHtml template_html1() {
      return template.html1();
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 1 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenBundle) GWT.create(org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenBundle.class);
      // Setup section.


      return clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay;
    }

    /**
     * Getter for playerStyle called 7 times. Type: GENERATED_CSS. Build precedence: 1.
     */
    private org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenCss_playerStyle playerStyle;
    private org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenCss_playerStyle get_playerStyle() {
      return playerStyle;
    }
    private org.ednovo.gooru.client.uc.TocCollectionEndView_TocCollectionEndViewUiBinderImpl_GenCss_playerStyle build_playerStyle() {
      // Creation section.
      playerStyle = get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay().playerStyle();
      // Setup section.
      playerStyle.ensureInjected();


      return playerStyle;
    }

    /**
     * Getter for endContainer called 1 times. Type: DEFAULT. Build precedence: 1.
     */
    private com.google.gwt.user.client.ui.FlowPanel get_endContainer() {
      return build_endContainer();
    }
    private com.google.gwt.user.client.ui.FlowPanel build_endContainer() {
      // Creation section.
      final com.google.gwt.user.client.ui.FlowPanel endContainer = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);
      // Setup section.
      endContainer.add(get_endImageContainer());
      endContainer.setStyleName("" + get_playerStyle().collectionHomeContainer() + "");


      owner.endContainer = endContainer;

      return endContainer;
    }

    /**
     * Getter for endImageContainer called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.FlowPanel get_endImageContainer() {
      return build_endImageContainer();
    }
    private com.google.gwt.user.client.ui.FlowPanel build_endImageContainer() {
      // Creation section.
      final com.google.gwt.user.client.ui.FlowPanel endImageContainer = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);
      // Setup section.
      endImageContainer.add(get_f_FlowPanel1());
      endImageContainer.add(get_resourceTitle());
      endImageContainer.setStyleName("" + get_playerStyle().collectionTopHomeContainer() + "");


      owner.endImageContainer = endImageContainer;

      return endImageContainer;
    }

    /**
     * Getter for f_FlowPanel1 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.FlowPanel get_f_FlowPanel1() {
      return build_f_FlowPanel1();
    }
    private com.google.gwt.user.client.ui.FlowPanel build_f_FlowPanel1() {
      // Creation section.
      final com.google.gwt.user.client.ui.FlowPanel f_FlowPanel1 = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);
      // Setup section.
      f_FlowPanel1.add(get_f_Label2());
      f_FlowPanel1.add(get_f_Label3());
      f_FlowPanel1.add(get_resourceThumbnail());
      f_FlowPanel1.setStyleName("" + get_playerStyle().collectionHomeThumbnail() + "");


      return f_FlowPanel1;
    }

    /**
     * Getter for f_Label2 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label2() {
      return build_f_Label2();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label2() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label2 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label2.setStyleName("" + get_playerStyle().collectionHomeType() + "");


      return f_Label2;
    }

    /**
     * Getter for f_Label3 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label3() {
      return build_f_Label3();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label3() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label3 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label3.setStyleName("" + get_playerStyle().collectionHomeOverlay() + "");


      return f_Label3;
    }

    /**
     * Getter for resourceThumbnail called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Image get_resourceThumbnail() {
      return build_resourceThumbnail();
    }
    private com.google.gwt.user.client.ui.Image build_resourceThumbnail() {
      // Creation section.
      final com.google.gwt.user.client.ui.Image resourceThumbnail = (com.google.gwt.user.client.ui.Image) GWT.create(com.google.gwt.user.client.ui.Image.class);
      // Setup section.
      resourceThumbnail.setStyleName("" + get_playerStyle().collectionHomeImage() + "");
      resourceThumbnail.addErrorHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      owner.resourceThumbnail = resourceThumbnail;

      return resourceThumbnail;
    }

    /**
     * Getter for resourceTitle called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_resourceTitle() {
      return build_resourceTitle();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_resourceTitle() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel resourceTitle = new com.google.gwt.user.client.ui.HTMLPanel(template_html1().asString());
      // Setup section.
      resourceTitle.setStyleName("" + get_playerStyle().collectionHomeTitle() + "");


      owner.resourceTitle = resourceTitle;

      return resourceTitle;
    }
  }
}
