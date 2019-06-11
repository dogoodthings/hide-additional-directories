package org.dogoodthings.ectr.osgi;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.applfilehandling.ApplFileInfo;
import com.dscsag.plm.spi.interfaces.applfilehandling.ApplicationFileHandler;

/**
 * sets the MS-DOS "hidden" attribute for all additional directories
 * this works only on windows
 *
 */
public class HideAdditionalDirectoriesHandler implements ApplicationFileHandler
{
  private ECTRService service;
  private String applicationType;

  public HideAdditionalDirectoriesHandler(String applicationType, ECTRService service)
  {
    this.applicationType = applicationType;
    this.service = service;
  }

  @Override
  public String getApplType()
  {
    return applicationType;
  }

  @Override
  public void filesProvided(Collection<ApplFileInfo> applFiles)
  {
    if (applFiles != null && applFiles.size() > 0)
    {
      if (OsUtil.isWindows())
        hideFilesOnWindows(applFiles);
      else
        service.getPlmLogger().warning("Don't know how to hide files on operating system " + OsUtil.getOS());
    }
  }

  @Override
  public void filesRemoved(Collection<ApplFileInfo> applFiles)
  {
    // empty, no needed
  }

  private void hideFilesOnWindows(Collection<ApplFileInfo> applicationFileInfo)
  {
    applicationFileInfo.stream()
        .map(fileInfo -> fileInfo.getAdditionalDirectory().toPath())
        .forEach(addDirPath ->
        {
          try
          {
            service.getPlmLogger().debug("Hiding '" + addDirPath.toString() + "'");
            Files.setAttribute(addDirPath, "dos:hidden", true);
          }
          catch (IOException e)
          {
            service.getPlmLogger().error("Can't hide '" + addDirPath + "'");
            service.getPlmLogger().error(e);
          }
        });
  }

}