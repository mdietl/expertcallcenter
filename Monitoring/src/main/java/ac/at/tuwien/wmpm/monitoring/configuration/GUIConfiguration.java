package ac.at.tuwien.wmpm.monitoring.configuration;

import ac.at.tuwien.wmpm.monitoring.gui.MainGUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Configuration
public class GUIConfiguration {

  /**
   * Main gui.
   *
   * @return the main gui
   */
  @Bean
  public MainGUI mainGUI() {
    return new MainGUI();
  }
}
