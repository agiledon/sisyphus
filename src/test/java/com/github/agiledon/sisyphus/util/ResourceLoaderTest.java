package com.github.agiledon.sisyphus.util;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourceLoaderTest {
    @Test
    public void should_split_sections_by_section_indicator() {
        List<List<String>> lists = ResourceLoader.loadResources("userWithMultiSections.json");

        assertThat(lists.size(), is(3));
        List<String> firstSection = lists.get(0);
        assertThat(firstSection.get(0), is("firstName = \"Joe\""));

        List<String> secondSection = lists.get(1);
        assertThat(secondSection.get(0), is("firstName = \"Bruce\""));

        List<String> thirdSection = lists.get(2);
        assertThat(thirdSection.get(0), is("firstName = \"Yi\""));
    }
}
