package org.ednovo.gooru.client.mvp.play.collection.info;

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

public class ResourceCollectionView_ResourceCollectionViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView>, org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView.ResourceCollectionViewUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<span id='{0}'></span>")
    SafeHtml html1(String arg0);
     
    @Template("<span id='{0}'></span>")
    SafeHtml html2(String arg0);
     
    @Template("<span id='{0}'></span> <span id='{1}'></span>")
    SafeHtml html3(String arg0, String arg1);
     
    @Template("")
    SafeHtml html4();
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView owner) {


    return new Widgets(owner).get_f_FlowPanel1();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView owner;


    final com.google.gwt.event.dom.client.ErrorHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.google.gwt.event.dom.client.ErrorHandler() {
      public void onError(com.google.gwt.event.dom.client.ErrorEvent event) {
        owner.setDefaultCollectionThumbnail(event);
      }
    };

    public Widgets(final org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView owner) {
      this.owner = owner;
      build_playerStyle();  // generated css resource must be always created. Type: GENERATED_CSS. Precedence: 1
      build_domId1();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 4
      build_domId2();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 4
      build_domId3();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 4
      build_domId0();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 3
      build_domId1Element();  // more than one getter call detected. Type: DEFAULT, precedence: 4
      build_domId2Element();  // more than one getter call detected. Type: DEFAULT, precedence: 4
      build_domId3Element();  // more than one getter call detected. Type: DEFAULT, precedence: 4
      build_domId0Element();  // more than one getter call detected. Type: DEFAULT, precedence: 3
    }

    SafeHtml template_html1() {
      return template.html1(get_domId1());
    }
    SafeHtml template_html2() {
      return template.html2(get_domId0());
    }
    SafeHtml template_html3() {
      return template.html3(get_domId2(), get_domId3());
    }
    SafeHtml template_html4() {
      return template.html4();
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 1 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenBundle) GWT.create(org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenBundle.class);
      // Setup section.


      return clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay;
    }

    /**
     * Getter for playerStyle called 9 times. Type: GENERATED_CSS. Build precedence: 1.
     */
    private org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenCss_playerStyle playerStyle;
    private org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenCss_playerStyle get_playerStyle() {
      return playerStyle;
    }
    private org.ednovo.gooru.client.mvp.play.collection.info.ResourceCollectionView_ResourceCollectionViewUiBinderImpl_GenCss_playerStyle build_playerStyle() {
      // Creation section.
      playerStyle = get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay().playerStyle();
      // Setup section.
      playerStyle.ensureInjected();


      return playerStyle;
    }

    /**
     * Getter for f_FlowPanel1 called 1 times. Type: DEFAULT. Build precedence: 1.
     */
    private com.google.gwt.user.client.ui.FlowPanel get_f_FlowPanel1() {
      return build_f_FlowPanel1();
    }
    private com.google.gwt.user.client.ui.FlowPanel build_f_FlowPanel1() {
      // Creation section.
      final com.google.gwt.user.client.ui.FlowPanel f_FlowPanel1 = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);
      // Setup section.
      f_FlowPanel1.add(get_f_HTMLPanel2());
      f_FlowPanel1.add(get_f_FlowPanel3());
      f_FlowPanel1.setStyleName("" + get_playerStyle().collectionResourceContainer() + "");


      return f_FlowPanel1;
    }

    /**
     * Getter for f_HTMLPanel2 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_f_HTMLPanel2() {
      return build_f_HTMLPanel2();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_f_HTMLPanel2() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel2 = new com.google.gwt.user.client.ui.HTMLPanel(template_html2().asString());
      // Setup section.
      f_HTMLPanel2.setStyleName("" + get_playerStyle().collectionThumbnailContainer() + "");

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord277 = UiBinderUtil.attachToDom(f_HTMLPanel2.getElement());
      get_domId0Element().get();

      // Detach section.
      attachRecord277.detach();
      f_HTMLPanel2.addAndReplaceElement(get_imageContainer(), get_domId0Element().get());

      return f_HTMLPanel2;
    }

    /**
     * Getter for domId0 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 3.
     */
    private java.lang.String domId0;
    private java.lang.String get_domId0() {
      return domId0;
    }
    private java.lang.String build_domId0() {
      // Creation section.
      domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId0;
    }

    /**
     * Getter for imageContainer called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_imageContainer() {
      return build_imageContainer();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_imageContainer() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel imageContainer = new com.google.gwt.user.client.ui.HTMLPanel(template_html1().asString());
      // Setup section.

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord278 = UiBinderUtil.attachToDom(imageContainer.getElement());
      get_domId1Element().get();

      // Detach section.
      attachRecord278.detach();
      imageContainer.addAndReplaceElement(get_collectionThumbnail(), get_domId1Element().get());

      owner.imageContainer = imageContainer;

      return imageContainer;
    }

    /**
     * Getter for domId1 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 4.
     */
    private java.lang.String domId1;
    private java.lang.String get_domId1() {
      return domId1;
    }
    private java.lang.String build_domId1() {
      // Creation section.
      domId1 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId1;
    }

    /**
     * Getter for collectionThumbnail called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Image get_collectionThumbnail() {
      return build_collectionThumbnail();
    }
    private com.google.gwt.user.client.ui.Image build_collectionThumbnail() {
      // Creation section.
      final com.google.gwt.user.client.ui.Image collectionThumbnail = (com.google.gwt.user.client.ui.Image) GWT.create(com.google.gwt.user.client.ui.Image.class);
      // Setup section.
      collectionThumbnail.setStyleName("" + get_playerStyle().collectionThumbnail() + "");
      collectionThumbnail.addErrorHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      owner.collectionThumbnail = collectionThumbnail;

      return collectionThumbnail;
    }

    /**
     * Getter for domId1Element called 2 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId1Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId1Element() {
      return domId1Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId1Element() {
      // Creation section.
      domId1Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId1());
      // Setup section.


      return domId1Element;
    }

    /**
     * Getter for domId0Element called 2 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId0Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId0Element() {
      return domId0Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId0Element() {
      // Creation section.
      domId0Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId0());
      // Setup section.


      return domId0Element;
    }

    /**
     * Getter for f_FlowPanel3 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.FlowPanel get_f_FlowPanel3() {
      return build_f_FlowPanel3();
    }
    private com.google.gwt.user.client.ui.FlowPanel build_f_FlowPanel3() {
      // Creation section.
      final com.google.gwt.user.client.ui.FlowPanel f_FlowPanel3 = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);
      // Setup section.
      f_FlowPanel3.add(get_collectionTitle());
      f_FlowPanel3.add(get_f_HTMLPanel4());
      f_FlowPanel3.add(get_metadataContainer());
      f_FlowPanel3.setStyleName("" + get_playerStyle().collectionMetadataContainer() + "");


      return f_FlowPanel3;
    }

    /**
     * Getter for collectionTitle called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Anchor get_collectionTitle() {
      return build_collectionTitle();
    }
    private com.google.gwt.user.client.ui.Anchor build_collectionTitle() {
      // Creation section.
      final com.google.gwt.user.client.ui.Anchor collectionTitle = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
      // Setup section.
      collectionTitle.setStyleName("" + get_playerStyle().collectionMetadataTitle() + "");


      owner.collectionTitle = collectionTitle;

      return collectionTitle;
    }

    /**
     * Getter for f_HTMLPanel4 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_f_HTMLPanel4() {
      return build_f_HTMLPanel4();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_f_HTMLPanel4() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel4 = new com.google.gwt.user.client.ui.HTMLPanel(template_html3().asString());
      // Setup section.
      f_HTMLPanel4.setStyleName("" + get_playerStyle().collectionAuthorContainer() + "");

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord279 = UiBinderUtil.attachToDom(f_HTMLPanel4.getElement());
      get_domId2Element().get();
      get_domId3Element().get();

      // Detach section.
      attachRecord279.detach();
      f_HTMLPanel4.addAndReplaceElement(get_createdByText(), get_domId2Element().get());
      f_HTMLPanel4.addAndReplaceElement(get_userName(), get_domId3Element().get());

      return f_HTMLPanel4;
    }

    /**
     * Getter for domId2 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 4.
     */
    private java.lang.String domId2;
    private java.lang.String get_domId2() {
      return domId2;
    }
    private java.lang.String build_domId2() {
      // Creation section.
      domId2 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId2;
    }

    /**
     * Getter for createdByText called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Label get_createdByText() {
      return build_createdByText();
    }
    private com.google.gwt.user.client.ui.Label build_createdByText() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label createdByText = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      createdByText.setStyleName("" + get_playerStyle().collectionAuthorTitle() + "");


      owner.createdByText = createdByText;

      return createdByText;
    }

    /**
     * Getter for domId2Element called 2 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId2Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId2Element() {
      return domId2Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId2Element() {
      // Creation section.
      domId2Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId2());
      // Setup section.


      return domId2Element;
    }

    /**
     * Getter for domId3 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 4.
     */
    private java.lang.String domId3;
    private java.lang.String get_domId3() {
      return domId3;
    }
    private java.lang.String build_domId3() {
      // Creation section.
      domId3 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId3;
    }

    /**
     * Getter for userName called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Label get_userName() {
      return build_userName();
    }
    private com.google.gwt.user.client.ui.Label build_userName() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label userName = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      userName.setStyleName("" + get_playerStyle().collectionAuthorName() + "");


      owner.userName = userName;

      return userName;
    }

    /**
     * Getter for domId3Element called 2 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId3Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId3Element() {
      return domId3Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId3Element() {
      // Creation section.
      domId3Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId3());
      // Setup section.


      return domId3Element;
    }

    /**
     * Getter for metadataContainer called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_metadataContainer() {
      return build_metadataContainer();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_metadataContainer() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel metadataContainer = new com.google.gwt.user.client.ui.HTMLPanel(template_html4().asString());
      // Setup section.
      metadataContainer.setStyleName("" + get_playerStyle().collectionInnermetadataContainer() + "");


      owner.metadataContainer = metadataContainer;

      return metadataContainer;
    }
  }
}
