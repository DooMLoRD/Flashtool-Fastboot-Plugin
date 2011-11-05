package org.plugins.fastbootplugin;

import java.io.File;
import java.io.FileFilter;

public class kernelimgFileFilter implements FileFilter
{
	private final String[] okFileExtensions = 
		new String[] {"img"};

  public boolean accept(File file)
  {
    for (String extension : okFileExtensions)
    {
      if (file.getName().toLowerCase().endsWith(extension) && file.isFile())
      {
        return true;
      }
    }
    return false;
  }
}
