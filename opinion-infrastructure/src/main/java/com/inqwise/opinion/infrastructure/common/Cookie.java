package com.inqwise.opinion.infrastructure.common;

import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class Cookie implements ICookie {

	private final String name;
    private String value;
    private String domain;
    private String path;
    private String comment;
    private Integer maxAge = Integer.MIN_VALUE;
    private int version;
    private boolean secure;
    private boolean httpOnly;


    /**
     * Creates a new cookie with the specified name and value.
     */
    public Cookie(String name, String value) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }


        for (int i = 0; i < name.length(); i ++) {
            char c = name.charAt(i);
            if (c > 127) {
                throw new IllegalArgumentException(
                        "name contains non-ascii character: " + name);
            }


            // Check prohibited characters.
            switch (c) {
            case '\t': case '\n': case 0x0b: case '\f': case '\r':
            case ' ':  case ',':  case ';':  case '=':
                throw new IllegalArgumentException(
                        "name contains one of the following prohibited characters: " +
                        "=,; \\t\\r\\n\\v\\f: " + name);
            }
        }


        if (name.charAt(0) == '$') {
            throw new IllegalArgumentException("name starting with '$' not allowed: " + name);
        }


        this.name = name;
        setValue(value);
    }


    public Cookie(JSONObject input) {
		this(JSONHelper.optStringTrim(input, "name"), JSONHelper.optString(input, "value"));
		setDomain(JSONHelper.optString(input, "domain"));
		setPath(JSONHelper.optString(input, "path"));
		setMaxAge(JSONHelper.optInt(input, "maxAge"));
		setSecure(JSONHelper.optBoolean(input, "secure"));
	}


	@Override
    public String getName() {
        return name;
    }


    @Override
    public String getValue() {
        return value;
    }


    @Override
    public void setValue(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value = value;
    }


    @Override
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = validateValue("domain", domain);
    }


    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = validateValue("path", path);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = validateValue("comment", comment);
    }

    @Override
    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public int hashCode() {
        return getName().hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cookie)) {
            return false;
        }


        Cookie that = (Cookie) o;
        if (!getName().equalsIgnoreCase(that.getName())) {
            return false;
        }


        if (getPath() == null) {
            if (that.getPath() != null) {
                return false;
            }
        } else if (that.getPath() == null) {
            return false;
        } else if (!getPath().equals(that.getPath())) {
            return false;
        }


        if (getDomain() == null) {
            if (that.getDomain() != null) {
                return false;
            }
        } else if (that.getDomain() == null) {
            return false;
        } else {
            return getDomain().equalsIgnoreCase(that.getDomain());
        }


        return true;
    }

    public int compareTo(Cookie c) {
        int v;
        v = getName().compareToIgnoreCase(c.getName());
        if (v != 0) {
            return v;
        }


        if (getPath() == null) {
            if (c.getPath() != null) {
                return -1;
            }
        } else if (c.getPath() == null) {
            return 1;
        } else {
            v = getPath().compareTo(c.getPath());
            if (v != 0) {
                return v;
            }
        }


        if (getDomain() == null) {
            if (c.getDomain() != null) {
                return -1;
            }
        } else if (c.getDomain() == null) {
            return 1;
        } else {
            v = getDomain().compareToIgnoreCase(c.getDomain());
            return v;
        }


        return 0;
    }


    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getName());
        buf.append('=');
        buf.append(getValue());
        if (getDomain() != null) {
            buf.append(", domain=");
            buf.append(getDomain());
        }
        if (getPath() != null) {
            buf.append(", path=");
            buf.append(getPath());
        }
        if (getComment() != null) {
            buf.append(", comment=");
            buf.append(getComment());
        }
        if (getMaxAge() >= 0) {
            buf.append(", maxAge=");
            buf.append(getMaxAge());
            buf.append('s');
        }
        if (isSecure()) {
            buf.append(", secure");
        }
        if (isHttpOnly()) {
            buf.append(", HTTPOnly");
        }
        return buf.toString();
    }

    private static String validateValue(String name, String value) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.isEmpty()) {
            return null;
        }
        for (int i = 0; i < value.length(); i ++) {
            char c = value.charAt(i);
            switch (c) {
            case '\r': case '\n': case '\f': case 0x0b: case ';':
                throw new IllegalArgumentException(
                        name + " contains one of the following prohibited characters: " +
                        ";\\r\\n\\f\\v (" + value + ')');
            }
        }
        return value;
    }


	public Cookie withPath(String path) {
		setPath(path);
		return this;
	}


	public ICookie withMaxAge(Integer maxAge) {
		setMaxAge(maxAge);
		return this;
	}
}
