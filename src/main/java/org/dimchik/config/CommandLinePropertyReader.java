package org.dimchik.config;

public class CommandLinePropertyReader implements PropertyReader {

    private final String[] args;

    public CommandLinePropertyReader(String[] args) {
        this.args = args;
    }

    private String getArg(String key) {
        for (String arg : args) {
            if (arg.startsWith(key + "=")) {
                return arg.substring(key.length() + 1);
            }
        }
        return null;
    }

    @Override
    public String url() {
        return getArg("-url");
    }

    @Override
    public String userName() {
        return getArg("-user");
    }

    @Override
    public String password() {
        return getArg("-password");
    }
}
