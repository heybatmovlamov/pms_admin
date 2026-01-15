package pms_core.config;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class JSON {

    private final Parser parser;

    public JSON(String json) {
        if (json == null) {
            throw new IllegalArgumentException("Value can't be null");
        } else {
            Tokenizer tokenizer = new Tokenizer(new Scanner(json));
            this.parser = new Parser(tokenizer);
        }
    }

    public java.lang.Object parse(Boolean wrap) throws ParseException, IOException {
        return this.parser.parse(wrap);
    }

    public List parseList() throws ParseException, IOException {
        return (List) this.parser.parse(false);
    }

    public List parseList(Boolean wrap) throws ParseException, IOException {
        return (List) this.parser.parse(wrap);
    }

    public Map<String, java.lang.Object> parseHash() throws ParseException, IOException {
        return (Map) this.parser.parse(false);
    }

    public Object parseObject() throws ParseException, IOException {
        return (Object) this.parser.parse(true);
    }

    public static String serialize(java.lang.Object object) {
        StringBuilder builder = new StringBuilder();
        if (object == null) {
            return null;
        } else if (object instanceof Boolean) {
            return object.toString();
        } else if (object instanceof Date) {
            return String.format("\"%s\"", object.toString());
        } else if (!(object instanceof String) && !(object instanceof Character) && !object.getClass().isEnum()) {
            if (object instanceof Number) {
                return object.toString();
            } else {
                Iterator var13;
                java.lang.Object item;
                if (object instanceof List) {
                    for (var13 = ((List) object).iterator(); var13.hasNext(); builder.append(serialize(item))) {
                        item = var13.next();
                        if (builder.length() != 0) {
                            builder.append(", ");
                        }
                    }

                    return String.format("[%s]", builder.toString());
                } else if (object instanceof Map) {
                    var13 = ((Map) object).keySet().iterator();

                    while (var13.hasNext()) {
                        item = var13.next();
                        java.lang.Object value = serialize(((Map) object).get(item));
                        if (value != null) {
                            if (builder.length() != 0) {
                                builder.append(", ");
                            }

                            builder.append(String.format("\"%s\": %s", item, value != null ? value : "null"));
                        }
                    }

                    return String.format("{%s}", builder.toString());
                } else if (object.getClass().isArray()) {
                    for (int i = 0; i < Array.getLength(object); ++i) {
                        if (builder.length() != 0) {
                            builder.append(", ");
                        }

                        builder.append(serialize(Array.get(object, i)));
                    }

                    return String.format("[%s]", builder.toString());
                } else if (object.getClass().isPrimitive()) {
                    return object.toString();
                } else if (object instanceof Object) {
                    return ((Object) object).serialize();
                } else {
                    Class<?> type = object.getClass();
                    Access.By by = type.isAnnotationPresent(Access.class) ? ((Access) type.getAnnotation(Access.class)).value() : Access.By.Exposed;

                    try {
                        switch (by) {
                            case Exposed:
                            case Field:
                                List<Field> fieldList = Arrays.asList(object.getClass().getFields());
                                fieldList.sort(Comparator.comparing((element) -> {
                                    return element.isAnnotationPresent(Order.class) ? ((Order) element.getAnnotation(Order.class)).value() : 0;
                                }));
                                Iterator var5 = fieldList.iterator();

                                while (var5.hasNext()) {
                                    Field field = (Field) var5.next();
                                    if (!field.isAnnotationPresent(Omit.class)) {
                                        java.lang.Object value = serialize(field.get(object));
                                        if (value != null) {
                                            if (field.isAnnotationPresent(Date.class)) {
                                                if (field.getType() == java.util.Date.class) {
                                                    SimpleDateFormat format = new SimpleDateFormat(((Date) field.getAnnotation(Date.class)).value());
                                                    value = String.format("\"%s\"", format.format(field.get(object)));
                                                } else {
                                                    if (field.getType() != LocalDateTime.class) {
                                                        throw new RuntimeException(String.format("Date annotation can be used only with fields of java.util.Date and java.time.LocalDateTime type. Found: %s, field: %s", field.getType(), field));
                                                    }

                                                    ((LocalDateTime) field.get(object)).format(DateTimeFormatter.ofPattern(((Date) field.getAnnotation(Date.class)).value()));
                                                }
                                            }

                                            if (builder.length() != 0) {
                                                builder.append(", ");
                                            }

                                            builder.append(String.format("\"%s\": %s", field.isAnnotationPresent(Name.class) ? ((Name) field.getAnnotation(Name.class)).value() : field.getName(), value != null ? value : "null"));
                                        }
                                    }
                                }

                                if (by != Access.By.Exposed) {
                                    break;
                                }
                            case Property:
                                List<PropertyDescriptor> propertyList = (List) Arrays.asList(Introspector.getBeanInfo(object.getClass(), java.lang.Object.class).getPropertyDescriptors()).stream().filter((element) -> {
                                    return element.getReadMethod() != null;
                                }).collect(Collectors.toList());
                                propertyList.sort(Comparator.comparing((element) -> {
                                    return element.getReadMethod().isAnnotationPresent(Order.class) ? ((Order) element.getReadMethod().getAnnotation(Order.class)).value() : 0;
                                }));
                                Iterator var17 = propertyList.iterator();

                                while (var17.hasNext()) {
                                    PropertyDescriptor property = (PropertyDescriptor) var17.next();
                                    Method method = property.getReadMethod();
                                    if (!method.isAnnotationPresent(Omit.class)) {
                                        java.lang.Object value = serialize(method.invoke(object));
                                        if (value != null) {
                                            if (method.isAnnotationPresent(Date.class)) {
                                                if (method.getReturnType() == java.util.Date.class) {
                                                    SimpleDateFormat format = new SimpleDateFormat(((Date) method.getAnnotation(Date.class)).value());
                                                    value = String.format("\"%s\"", format.format(method.invoke(object)));
                                                } else {
                                                    if (method.getReturnType() != LocalDateTime.class) {
                                                        throw new RuntimeException(String.format("Date annotation can be used only with methods of java.util.Date and java.time.LocalDateTime return type. Found: %s, method: %s", method.getReturnType(), method));
                                                    }

                                                    ((LocalDateTime) method.invoke(object)).format(DateTimeFormatter.ofPattern(((Date) method.getAnnotation(Date.class)).value()));
                                                }
                                            }

                                            if (builder.length() != 0) {
                                                builder.append(", ");
                                            }

                                            builder.append(String.format("\"%s\": %s", method.isAnnotationPresent(Name.class) ? ((Name) method.getAnnotation(Name.class)).value() : property.getName(), value != null ? value : "null"));
                                        }
                                    }
                                }
                            case Marked:
                        }
                    } catch (InvocationTargetException | IntrospectionException | IllegalAccessException | IllegalArgumentException var11) {
                        throw new RuntimeException(var11);
                    }

                    return String.format("{%s}", builder.toString());
                }
            }
        } else {
            return String.format("\"%s\"", object.toString().replace("\"", "\\\"").replace("\t", "\\t"));
        }
    }

    public static Object createObject() {
        return new Object();
    }

//    public static void main(String[] args) throws ParseException, IOException {
//        lib.JSON.Object object = createObject();
//        object.put("person", createObject().put("name", "Fariz").put("surname", "Kazimov").put("age", 34).put("height", 168.5D).put("languages", new String[]{"az", "en", "ru"}).put("work", createObject().put("name", "YIGIM").put("address", "Izmir Plaza")));
//        String json = object.serialize();
//        lib.JSON.Object tmp = (new lib.JSON(json)).parseObject();
//        System.out.println(tmp.getObject("person").getString("name"));
//        System.out.println(tmp.getObject("person").getInt("age"));
//        System.out.println(tmp.getObject("person").getDouble("height"));
//        System.out.println(tmp.getObject("person").getList("languages"));
//    }

    public static class Object implements Serializable {

        private Map<String, java.lang.Object> hash;

        public Object getObject(String key) {
            return (Object) this.hash.get(key);
        }

        public List getList(String key) {
            return (List) this.hash.get(key);
        }

        public BigDecimal getBigDecimal(String key) {
            return BigDecimal.valueOf(this.getDouble(key));
        }

        public String getString(String key) {
            return (String) this.hash.get(key);
        }

        public String toString(String key) {
            java.lang.Object value = this.hash.get(key);
            return value == null ? null : value.toString();
        }

        public Integer getInt(String key) {
            java.lang.Object value = this.hash.get(key);
            return value == null ? null : ((Double) value).intValue();
        }

        public Long getLong(String key) {
            java.lang.Object value = this.hash.get(key);
            return value == null ? null : ((Double) value).longValue();
        }

        public Double getDouble(String key) {
            return (Double) this.hash.get(key);
        }

        public Object put(String key, java.lang.Object value) {
            this.hash.put(key, value);
            return this;
        }

        public String serialize() {
            return JSON.serialize(this.hash);
        }

        private Object() {
            this.hash = new LinkedHashMap();
        }

        private Object(Map<String, java.lang.Object> hash) {
            this.hash = new LinkedHashMap();
            this.hash = hash;
        }

        // $FF: synthetic method
        Object(Map x0, java.lang.Object x1) {
            this(x0);
        }

        // $FF: synthetic method
        Object(java.lang.Object x0) {
            this();
        }
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Date {

        String value();
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Omit {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Order {

        int value();
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Name {

        String value();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Access {

        By value();

        public static enum By {
            Exposed,
            Field,
            Marked,
            Property;
        }
    }

    public static class Scanner {

        private int lastCharacter;
        private int counter = 0;
        private final Reader reader;
        private boolean restored = false;

        public int getCharacterIndex() {
            return this.counter;
        }

        public Scanner(String string) {
            this.reader = new StringReader(string);
        }

        public int read() throws IOException {
            ++this.counter;
            if (this.restored) {
                this.restored = false;
                return this.lastCharacter;
            } else {
                return this.lastCharacter = this.reader.read();
            }
        }

        public int read(char[] c, int i, int j) throws IOException {
            c[i] = (char) this.read();
            int length = this.reader.read(c, i + 1, j - 1);
            this.counter += length;
            return length + 1;
        }

        public void restore() throws IOException {
            if (this.restored) {
                throw new IOException("Already restored");
            } else {
                this.restored = true;
                --this.counter;
            }
        }

        public void close() throws IOException {
            this.reader.close();
        }
    }

    private static class Token {

        private Type type;
        private java.lang.Object value;

        public Type getType() {
            return this.type;
        }

        public java.lang.Object getValue() {
            return this.value;
        }

        public Token(Type type, java.lang.Object value) {
            this.type = type;
            this.value = value;
        }

        public Token(Type type) {
            this.type = type;
        }

        public static enum Type {
            BOA,
            EOA,
            BOO,
            EOO,
            CLN,
            SPR,
            TRU,
            FLS,
            NLL,
            NBR,
            STR;
        }
    }

    private static class Parser {

        private Tokenizer tokenizer;
        private State state;

        public Parser(Tokenizer tokenizer) {
            this.state = State.None;
            this.tokenizer = tokenizer;
        }

        public java.lang.Object parse(Boolean wrap) throws ParseException, IOException {
            Token token = this.tokenizer.next();
            java.lang.Object value;
            Token.Type type;
            switch (token.getType()) {
                case EOO:
                    if (this.state != State.Key && this.state != State.HashSeparator) {
                        throw new ParseException("Unexpected EOO token", this.tokenizer.scanner.getCharacterIndex());
                    }

                    return null;
                case EOA:
                    if (this.state != State.Value) {
                        throw new ParseException("Unexpected EOA token", 0);
                    }

                    return null;
                case BOO:
                    if (this.state != State.None && this.state != State.Value) {
                        throw new ParseException("Unexpected BOO token", 0);
                    } else {
                        HashMap hash = new HashMap();

                        while (true) {
                            this.state = State.Key;
                            java.lang.Object key = this.parse(wrap);
                            if (key != null) {
                                if (this.tokenizer.next().type != Token.Type.CLN) {
                                    throw new ParseException("Expected ':' after object key", 0);
                                }

                                this.state = State.Value;
                                value = this.parse(wrap);
                                hash.put((String) key, value);
                                type = this.tokenizer.next().type;
                                switch (type) {
                                    case SPR:
                                        continue;
                                    case EOO:
                                        break;
                                    default:
                                        throw new ParseException(String.format("Expected SPR or EOO token, got %s token", token.getType()), this.tokenizer.scanner.getCharacterIndex());
                                }
                            }

                            return wrap ? new Object(hash) : hash;
                        }
                    }
                case BOA:
                    if (this.state != State.None && this.state != State.Value) {
                        throw new ParseException("Unexpected BOA token", this.tokenizer.scanner.getCharacterIndex());
                    } else {
                        ArrayList list = new ArrayList();

                        while (true) {
                            this.state = State.Value;
                            value = this.parse(wrap);
                            if (value != null) {
                                list.add(value);
                                type = this.tokenizer.next().type;
                                switch (type) {
                                    case SPR:
                                        continue;
                                    case EOA:
                                        break;
                                    default:
                                        throw new ParseException(String.format("Expected SPR or EOA token, got %s token", token.getType()), this.tokenizer.scanner.getCharacterIndex());
                                }
                            }

                            return list;
                        }
                    }
                case STR:
                    if (this.state != State.None && this.state != State.Key && this.state != State.Value) {
                        throw new ParseException(String.format("Expected %s, got %s token", this.state, token.getType()), this.tokenizer.scanner.getCharacterIndex());
                    }

                    return token.value;
                case NBR:
                case TRU:
                case FLS:
                case NLL:
                    if (this.state != State.None && this.state != State.Value) {
                        throw new ParseException(String.format("Expected %s, got %s token", this.state, token.getType()), this.tokenizer.scanner.getCharacterIndex());
                    }

                    return token.value;
                default:
                    throw new ParseException(String.format("Unexpected token %s", token.type), this.tokenizer.scanner.getCharacterIndex());
            }
        }

        private static enum State {
            None,
            Key,
            Value,
            Colon,
            ListSeparator,
            HashSeparator;
        }
    }

    public static class Tokenizer {

        private Scanner scanner;

        private Tokenizer(Scanner reader) {
            this.scanner = reader;
        }

        private Token next() throws ParseException, IOException {
            while (true) {
                int character = this.scanner.read();
                StringBuilder builder;
                switch (character) {
                    case 9:
                    case 10:
                    case 13:
                    case 32:
                        break;
                    case 34:
                        builder = new StringBuilder();

                        while (true) {
                            character = this.scanner.read();
                            switch (character) {
                                case -1:
                                    throw new ParseException("Unexpected end of stream", this.scanner.getCharacterIndex());
                                case 34:
                                    return new Token(Token.Type.STR, builder.toString());
                                case 92:
                                    switch (this.scanner.read()) {
                                        case 34:
                                            builder.append('"');
                                            continue;
                                        case 47:
                                            builder.append('/');
                                            continue;
                                        case 92:
                                            builder.append('\\');
                                            continue;
                                        case 98:
                                            builder.append('\b');
                                            continue;
                                        case 102:
                                            builder.append('\f');
                                            continue;
                                        case 110:
                                            builder.append('\n');
                                            continue;
                                        case 114:
                                            builder.append('\r');
                                            continue;
                                        case 116:
                                            builder.append('\t');
                                            continue;
                                        case 117:
                                            char[] buffer = new char[4];
                                            if (this.scanner.read(buffer, 0, 4) < 0) {
                                                throw new ParseException("Unexpected end of stream", this.scanner.getCharacterIndex());
                                            }

                                            builder.append((char) Integer.parseInt(new String(buffer, 0, 4), 16));
                                            continue;
                                        default:
                                            throw new ParseException("Unexpected character", this.scanner.getCharacterIndex());
                                    }
                                default:
                                    builder.append((char) character);
                            }
                        }
                    case 44:
                        return new Token(Token.Type.SPR);
                    case 45:
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        builder = new StringBuilder();
                        if (character != 48) {
                            do {
                                builder.append((char) character);
                                character = this.scanner.read();
                            } while (character >= 48 && character <= 57);
                        } else {
                            builder.append((char) character);
                            character = this.scanner.read();
                        }

                        if (character == 46) {
                            builder.append((char) character);
                            character = this.scanner.read();
                            if (character < 48 || character > 57) {
                                throw new ParseException(String.format("Unexpected character: %s", (char) character), this.scanner.getCharacterIndex());
                            }

                            do {
                                builder.append((char) character);
                                character = this.scanner.read();
                            } while (character >= 48 && character <= 57);
                        }

                        if (character == 101 || character == 69) {
                            label147:
                            {
                                builder.append((char) character);
                                character = this.scanner.read();
                                if (character == 45 || character == 43) {
                                    builder.append((char) character);
                                    character = this.scanner.read();
                                }

                                if (character >= 48 && character <= 57) {
                                    while (true) {
                                        builder.append((char) character);
                                        character = this.scanner.read();
                                        if (character < 48 || character > 57) {
                                            break label147;
                                        }
                                    }
                                }

                                throw new ParseException(String.format("Unexpected character: %s", (char) character), this.scanner.getCharacterIndex());
                            }
                        }

                        switch (character) {
                            case 44:
                            case 93:
                            case 125:
                                this.scanner.restore();
                            default:
                                return new Token(Token.Type.NBR, Double.parseDouble(builder.toString()));
                        }
                    case 58:
                        return new Token(Token.Type.CLN);
                    case 91:
                        return new Token(Token.Type.BOA);
                    case 93:
                        return new Token(Token.Type.EOA);
                    case 102:
                        if (this.scanner.read() == 97 && this.scanner.read() == 108 && this.scanner.read() == 115 && this.scanner.read() == 101) {
                            return new Token(Token.Type.FLS, false);
                        }

                        throw new ParseException("Invalid character", this.scanner.getCharacterIndex());
                    case 110:
                        if (this.scanner.read() == 117 && this.scanner.read() == 108 && this.scanner.read() == 108) {
                            return new Token(Token.Type.NLL, (java.lang.Object) null);
                        }

                        throw new ParseException("Invalid character", this.scanner.getCharacterIndex());
                    case 116:
                        if (this.scanner.read() == 114 && this.scanner.read() == 117 && this.scanner.read() == 101) {
                            return new Token(Token.Type.TRU, true);
                        }

                        throw new ParseException("Invalid character", this.scanner.getCharacterIndex());
                    case 123:
                        return new Token(Token.Type.BOO);
                    case 125:
                        return new Token(Token.Type.EOO);
                    case 65279:
                        if (this.scanner.getCharacterIndex() == 1) {
                            break;
                        }
                    default:
                        throw new ParseException(String.format("Unexpected character '%s' at %d", (char) character, this.scanner.getCharacterIndex()), this.scanner.getCharacterIndex());
                }
            }
        }

        public char move() throws IOException {
            return (char) this.scanner.read();
        }

        // $FF: synthetic method
        Tokenizer(Scanner x0, java.lang.Object x1) {
            this(x0);
        }
    }
}
