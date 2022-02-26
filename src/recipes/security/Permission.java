package recipes.security;

public enum Permission {

    RECIPE_READ("recipe:read"),
    RECIPE_WRITE("recipe:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
