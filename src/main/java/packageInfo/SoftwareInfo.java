package packageInfo;
import java.io.File;
import java.net.URISyntaxException;

public class SoftwareInfo {
    private SoftwareInfo() throws URISyntaxException {

    }
    static final String version = "v2.0.0";
    static final String buildMode=  "Release";
    static final String buildDate = "2202.9.5";
    static final String About = "Audio addon for JUIT Library";
    static final File icon;

    static {
        try {
            icon = new File(SoftwareInfo.class.getProtectionDomain().getCodeSource().getLocation().toURI() +"/src/main/java/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getVersion() {
        return version;
    }

    public static String getBuildMode() {
        return buildMode;
    }

    public static String getBuildDate() {
        return buildDate;
    }

    public static String getAbout() {
        return About;
    }

    public static File getIcon() {
        return icon;
    }
}
