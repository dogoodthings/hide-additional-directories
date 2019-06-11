package org.dogoodthings.ectr.osgi;

import java.util.Locale;

public class OsUtil
{

  public enum OsType
  {
    WINDOWS,
    UNIX,
    MAC_OS,
    OTHER
  }

  private static final String os;
  private static final OsType currentOsType;

  static  {
    os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
    if (os.startsWith("windows"))
      currentOsType = OsType.WINDOWS;
    else if (os.contains("nix") || os.contains("nux") || os.startsWith("sunos"))
      currentOsType = OsType.UNIX;
    else if (os.contains("mac") || os.contains("darwin") )
      currentOsType = OsType.MAC_OS;
    else
      currentOsType = OsType.OTHER;
  }

  private OsUtil()
  {
    // to nothing!
  }


  public static boolean isWindows()
  {
    return OsType.WINDOWS == currentOsType;
  }

  public static boolean isUnix()
  {
    return OsType.UNIX == currentOsType;
  }

  public static boolean isMacOs()
  {
    return OsType.MAC_OS == currentOsType;
  }

  public static String getOS()
  {
    return os;
  }
}