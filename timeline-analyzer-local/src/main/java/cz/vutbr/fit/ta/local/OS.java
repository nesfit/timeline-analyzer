/**
 * OS.java
 *
 * Created on 1. 2. 2017, 14:30:39 by burgetr
 */
package cz.vutbr.fit.ta.local;

/**
 * An abstraction over the OS properties.
 * 
 * @author burgetr
 */
public class OS
{
    private String name;
    private String version;
    private String arch;
    
    public OS()
    {
        name = System.getProperty("os.name");
        version = System.getProperty("os.version");
        arch = System.getProperty("os.arch");
    }

    @Override
    public String toString()
    {
        return "OS [name=" + name + ", version=" + version + ", arch=" + arch + "]";
    }

    public String getName()
    {
        return name;
    }

    public String getVersion()
    {
        return version;
    }

    public String getArch()
    {
        return arch;
    }

    public boolean isLinux()
    {
        return name.toLowerCase().contains("linux");
    }
    
    public boolean isWindows()
    {
        return name.toLowerCase().contains("windows");
    }
    
    public char pathSeparator()
    {
        return isWindows() ? '\\' : '/';
    }
    
    public String extractFilenameFromPath(String path)
    {
        final int pos = path.lastIndexOf(pathSeparator());
        if (pos == -1)
            return path;
        else if (pos + 1 <  path.length())
            return path.substring(pos + 1);
        else
            return "";
    }
    
}
