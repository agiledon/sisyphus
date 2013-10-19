##Sisyphus

Sisyphus is the mini framework to deserialize the data to the specific object for unit and integrated tests based on JVM paltform. It supports Json, Yaml and customized ASN format. 

In some projects, the developers need create the object which contains the data they need to test. For some objects, however, it is very tedious to create these objects. Of course, you are able to define the related factories or builders. If these java objects contains more than a dozen of fields, do you like do these things?

Sisyphus can help you create the objects you want to test. The only one thing you need do is to prepare the data file using json, yaml or other format. 

###How to use it
Sisyphus provide Fixture to encapsulate all logic to privide test data objects. 

#### Fetch Sisyphus

Current version of sisyphus is 0.1-snapshot, you can config you build script to manage the dependency for sisyphus:

```groovy
repositories {
    maven{
        url 'https://oss.sonatype.org/content/groups/public'
    }
    mavenCentral();
}

dependencies {
    test (
            'junit:junit:4.11',
            'com.github.agiledon:sisyphus:0.1-SNAPSHOT'
        )
}
```

#### JSON
If you want to prepare the user object as below:
```java
public class User {
    public enum Gender { MALE, FEMALE };

    public static class Name {
        private String _first, _last;

        public String getFirst() { return _first; }
        public String getLast() { return _last; }

        public void setFirst(String s) { _first = s; }
        public void setLast(String s) { _last = s; }
    }

    private Gender _gender;
    private Name _name;
    private boolean _isVerified;
    private byte[] _userImage;

    public Name getName() { return _name; }
    public boolean isVerified() { return _isVerified; }
    public Gender getGender() { return _gender; }
    public byte[] getUserImage() { return _userImage; }

    public void setName(Name n) { _name = n; }
    public void setVerified(boolean b) { _isVerified = b; }
    public void setGender(Gender g) { _gender = g; }
    public void setUserImage(byte[] b) { _userImage = b; }
}
```

You can prepare the user.json data file:
```
{
  "name" : { "first" : "Joe", "last" : "Sixpack" },
  "gender" : "MALE",
  "verified" : false,
  "userImage" : "Rm9vYmFyIQ=="
}
```
Then, write your test using Fixture provided by sisyphus:
```java
    @Test
    public void should_compose_User_data_with_json_format() {
        User user = Fixture.from("user.json").to(User.class);
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
    }
```

**Note: the extension of test data must be "json".**

Sisyphus uses Jackson to parse json data. But, Jackson require the target class must define the default constructor, if no, you need use annotation(@JsonCreate and @JsonProperty) provided by Jackson to annotate the constructor and parameters. In some cases, it is not suitable. For example, the system doesn't permit you modify the source code. So, sisyphus provides the second option：using Gson framework. To distinguish them, I use gson to demonstrate the another json data.

#### GSON
If you want to prepare the user object as below:
```java
public class StandardVariable {
    public String result;
    public String id;

    public StandardVariable(String result,
                            String id) {
        this.result = result;
        this.id = id;
    }
}
```

You can prepare the user.json data file:
```
[
    {
        "result" : "0",
         "id" : "id0"
    },
    {
        "result" : "1",
         "id" : "id1"
    },
    {
        "result" : "2",
         "id" : "id2"
    }
]
```
Then, write your test using Fixture provided by sisyphus:
```java
    @Test
    public void should_compose_StandardVariables_data_with_gson_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));
        assertThat(standardVariables[0].result, is("0"));
        assertThat(standardVariables[1].result, is("1"));
        assertThat(standardVariables[2].result, is("2"));
    }
```
**Note: the extension of test data must be "json".**

#### Yaml
Sisyphus use SnakeYaml to parse yaml data. The test data sample like this:
```
Type1:
    [Type1sub1,Type1sub2]
Type2:
    [Type2sub1,Type2sub2]
```

Using Fixture:
```java
    @Test
    public void should_compose_Type_data_with_yaml_format() {
        Map<String, List<String>> types = Fixture.from("type.yaml").to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }
```

#### Template

Sisyphus use ST4 to support template for all formats. Take an example for Gson. At first, you must provide the template file which extension should be "template", and use the character "$" to indicate variable (filename: standardVariable.template):
```
[
    {
        "result" : $result0$,
         "id" : "id0"
    },
    {
        "result" : $result1$,
         "id" : "id1"
    },
    {
        "result" : $result2$,
         "id" : "id2"
    }
]
```

Then you can provide the data file(filename: standardVariableWithTemplate.gson):
```
result0 = 0
result1 = 1
result2 = 2
```

The test method seems like:
```java
    @Test
    public void should_compose_StandardVariables_data_by_parsing_template_file() {
        StandardVariable[] standardVariables = Fixture.from("standardVariableWithTemplate.gson")
                .withTemplate("template/standardVariable.template")
                .to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));
        assertThat(standardVariables[0].result, is("0"));
        assertThat(standardVariables[1].result, is("1"));
        assertThat(standardVariables[2].result, is("2"));
    }
```

#### Caching

To imporve the performance of running tests, sisyphus provide the caching feature:
```java
    @Test
    public void should_compose_User_data_from_cache_with_json_format() {
        User user = Fixture.from("user.json").to(User.class);
        assertThat(user, not(nullValue()));

        User cachedUser = Fixture.from("user.json").to(User.class);
        assertThat(cachedUser, not(nullValue()));

        assertThat(Objects.equal(user, cachedUser), is(true));
    }
    
        @Test
    public void should_compose_User_data_from_cache_with_gson_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));

        StandardVariable[] cachedStandardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(cachedStandardVariables, not(nullValue()));

        assertThat(Objects.equal(standardVariables, cachedStandardVariables), is(true));
    }
```


###Todo
* Support JUnit and TestNG annotation.

### Frameworks
* Sisyphus use these frameworks: Guava, snakeyaml, jackson, gson, st4(StringTemplate), slf4j.

