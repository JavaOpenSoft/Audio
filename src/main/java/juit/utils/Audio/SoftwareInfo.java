package juit.utils.Audio;
import java.io.File;

public class SoftwareInfo {
    private SoftwareInfo() {

    }
    final String version = "v1.1.0";
    final String buildMode=  "Release";
    final String buildDate = "2202.9.3";
    final String About = "Audio addon for JUIT Library";
    final File icon = new File("");

    public String getVersion() {
        return version;
    }

    public String getBuildMode() {
        return buildMode;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getAbout() {
        return About;
    }

    public File getIcon() {
        return icon;
    }
}
