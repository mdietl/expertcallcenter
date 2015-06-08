package ac.at.tuwien.wmpm.coordinator.routes;

import java.io.File;

public class FilePolling {
  
  Long timeDifference = 1000L;
  
  public boolean accept(File file) {

  long lastModified = file.lastModified();
  long currentTime = System.currentTimeMillis();

  return (currentTime - lastModified) > timeDifference ;
  }

  public void setTimeDifference(Long timeDifference) {
       this.timeDifference = timeDifference;
  }

  public FilePolling() {
    
    
  }

}
