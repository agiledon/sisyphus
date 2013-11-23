package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SyntaxParser;

public class SisPrinter extends AbstractPrinter {
    @Override
    protected String getExtension() {
        return "sis";
    }

    @Override
    protected <T> String serialize(T sourceObject) {
        SyntaxParser syntaxParser = new SyntaxParser();
        SisClass sisClass = syntaxParser.parseClassFromObject(sourceObject);
        return sisClass.toString();
    }
}
