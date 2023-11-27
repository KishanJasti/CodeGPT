package ee.carlrobert.codegpt.settings.state;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import ee.carlrobert.codegpt.credentials.PalCredentialsManager;
import ee.carlrobert.codegpt.settings.service.ServiceSelectionForm;
import ee.carlrobert.llm.client.pal.completion.chat.PalChatCompletionModel;
import org.jetbrains.annotations.NotNull;

@State(name = "CodeGPT_PalSettings_210", storages = @Storage("CodeGPT_PalSettings_210.xml"))
public class PalSettingsState implements PersistentStateComponent<PalSettingsState> {

  private String baseHost;
  private String model = PalChatCompletionModel.GPT_3_5.getCode();;

  public static PalSettingsState getInstance() {
    return ApplicationManager.getApplication().getService(PalSettingsState.class);
  }

  @Override
  public PalSettingsState getState() {
    return this;
  }

  public boolean isModified(ServiceSelectionForm serviceSelectionForm) {
    return !serviceSelectionForm.getPalApiKey()
            .equals(PalCredentialsManager.getInstance().getPalApiKey())
            || !serviceSelectionForm.getPalBaseHost().equals(baseHost)
            || !serviceSelectionForm.getPalModel().equals(model);
  }
  @Override
  public void loadState(@NotNull PalSettingsState state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public void reset(ServiceSelectionForm serviceSelectionForm) {
    serviceSelectionForm.setPalApiKey(PalCredentialsManager.getInstance().getPalApiKey());
    serviceSelectionForm.setPalBaseHost(baseHost);
    serviceSelectionForm.setPalModel(model);
  }

  public void apply(ServiceSelectionForm serviceSelectionForm) {
    baseHost = serviceSelectionForm.getPalBaseHost();
    model = serviceSelectionForm.getPalModel();
  }
  public void setBaseHost(String baseHost) {
    this.baseHost = baseHost;
  }

  public String getBaseHost() {
    return baseHost;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }
}
