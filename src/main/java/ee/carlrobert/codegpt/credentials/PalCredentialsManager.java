package ee.carlrobert.codegpt.credentials;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import ee.carlrobert.codegpt.settings.state.AzureSettingsState;
import org.jetbrains.annotations.Nullable;

@Service
public final class PalCredentialsManager {

  private static final CredentialAttributes palApiKeyCredentialAttributes =
      CredentialsUtil.createCredentialAttributes("PAL_API_KEY");

  private String palApiKey;

  private PalCredentialsManager() {
    palApiKey = CredentialsUtil.getPassword(palApiKeyCredentialAttributes);
  }

  public static PalCredentialsManager getInstance() {
    return ApplicationManager.getApplication().getService(PalCredentialsManager.class);
  }

  public String getSecret() {
    return palApiKey;
  }

  public @Nullable String getPalApiKey() {
    return palApiKey;
  }

  public void setApiKey(String palApiKey) {
    this.palApiKey = palApiKey;
    CredentialsUtil.setPassword(palApiKeyCredentialAttributes, palApiKey);
  }

  private boolean isKeySet() {
    return palApiKey != null && !palApiKey.isEmpty();
  }
}