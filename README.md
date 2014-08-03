[![Build Status](https://travis-ci.org/agiledon/sisyphus.png?branch=master)](https://travis-ci.org/agiledon/sisyphus)

##Sisyphus

Sisyphus is the mini framework to deserialize the data to the specific object for unit and integrated tests based on JVM paltform. It supports Json, Yaml and customized SIS format.

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
            'com.github.agiledon:sisyphus:1.0-SNAPSHOT'
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
invoice: 34843
date   : 2001-01-23
billTo: &id001
    given  : Chris
    family : Dumars
    address:
        lines: |
            458 Walkman Dr.
            Suite #292
        city    : Royal Oak
        state   : MI
        postal  : 48046
shipTo: *id001
products:
    - sku         : BL394D
      quantity    : 4
      description : Basketball
      price       : 450.00
    - sku         : BL4438H
      quantity    : 1
      description : Super Hoop
      price       : 2392.00
tax  : 251.42
total: 4443.52
comments:
    Late afternoon is best.
    Backup contact is Nancy
    Billsmer @ 338-4338.
```

The corresponding java classes are as below:
```java
public class Invoice {
    public Integer invoice; // invoice
    public String date; // date
    public Person billTo;// bill-to
    public Person shipTo;// ship-to
    public Product[] products;
    public Float tax;
    public Float total;
    public String comments;

}
public class Product {
    public String sku;
    public int quantity;
    public String description;
    public double price;
}
public class Person {
    public String given;
    public String family;
    public Address address;
}
public class Address {
    public String lines;
    public String city;
    public String state;
    public String postal;
}
```

Using Fixture:
```java
    @Test
    public void should_compose_invoice_data_with_yaml_format() {
        Invoice invoice = from("invoice.yaml").to(Invoice.class);
        assertThat(invoice, not(nullValue()));
        assertThat(invoice.date, is("2001-01-23"));
        assertThat(invoice.products.length, is(2));
        assertThat(invoice.products[0].description, is("Basketball"));
        assertThat(invoice.billTo.given, is("Chris"));
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

#### Multi Sections
To make use of template effectively, sisyphus provides the multi sections for data file. So, you don't need create the related data file for each test case. In fact, you can gather all data in the one file using a section indicator "///":
```
{
  "name" : { "first" : $firstName$, "last" : $lastName$ },
  "gender" : "MALE",
  "verified" : false,
  "userImage" : "Rm9vYmFyIQ=="
}
```

The data file is as below:
```
firstName = "Joe"
lastName = "Sixpack"

///

firstName = "Bruce"
lastName = "Zhang"

///

firstName = "Yi"
lastName = "Zhang"
```

Then you can invoke the toList() method to get List object:
```java
    @Test
    public void should_compose_multi_user_data_by_parsing_template_file() {
        List<User> users = from("userWithMultiSections.json")
                .withTemplate("template/user.template")
                .toList(User.class);
        
        assertThat(users, not(nullValue()));
        assertThat(users.get(0).getName().getFirst(), is("Joe"));
        assertThat(users.get(0).getName().getLast(), is("Sixpack"));
        assertThat(users.get(2).getName().getFirst(), is("Yi"));
        assertThat(users.get(2).getName().getLast(), is("Zhang"));
    }
```

#### Comments
To let all data files make sense, sisyphus introduce the comments indicator "#". It will ignore this line start with "#" when sisyphus parse the test data:
```
# This is multi section sample
firstName = "Joe"
lastName = "Sixpack"

///

firstName = "Bruce"
lastName = "Zhang"

///

firstName = "Yi"
lastName = "Zhang"
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

#### JUnit Support

It defines the DataProviderRule and DataResource annotation:
```java
public class DataProviderRuleTest {
    @Rule
    public DataProviderRule dataProvider = new DataProviderRule();

    @Test
    @DataResource(resourceName = "user.json", targetClass = User.class)
    public void should_compose_User_data_with_json_format() {
        User user = dataProvider.provideData();
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
    }

    @Test
    @DataResource(resourceName = "userWithTemplate.json",
            templateName = "template/user.template",
            targetClass = User.class)
    public void should_compose_user_data_by_parsing_template_file() {
        User user = dataProvider.provideData();
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
        assertThat(user.getName().getLast(), is("Sixpack"));
    }
}
```


### Frameworks
* Sisyphus use these frameworks: Guava, snakeyaml, jackson, gson, st4(StringTemplate), slf4j.

