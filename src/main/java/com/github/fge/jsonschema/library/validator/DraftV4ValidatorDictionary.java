/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonschema.library.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import com.github.fge.jsonschema.keyword.validator.common.DependenciesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.AllOfValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.AnyOfValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.DraftV4TypeValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.MaxPropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.MinPropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.MultipleOfValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.NotValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.OneOfValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.RequiredKeywordValidator;

import java.lang.reflect.Constructor;

/**
 * Draft v4 specific keyword validator constructors
 */
public final class DraftV4ValidatorDictionary
{
    private static final Dictionary<Constructor<? extends KeywordValidator>>
        DICTIONARY;

    private DraftV4ValidatorDictionary()
    {
    }

    public static Dictionary<Constructor<? extends KeywordValidator>> get()
    {
        return DICTIONARY;
    }

    static {
        final DictionaryBuilder<Constructor<? extends KeywordValidator>>
            builder = Dictionary.newBuilder();

        String keyword;
        Class<? extends KeywordValidator> c;

        builder.addAll(CommonValidatorDictionary.get());

        /*
         * Number/integer
         */
        keyword = "multipleOf";
        c = MultipleOfValidator.class;
        builder.addEntry(keyword, constructor(c));

        /*
         * Object
         */
        keyword = "minProperties";
        c = MinPropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "maxProperties";
        c = MaxPropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "required";
        c = RequiredKeywordValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "dependencies";
        c = DependenciesValidator.class;
        builder.addEntry(keyword, constructor(c));

        /*
         * All
         */
        keyword = "anyOf";
        c = AnyOfValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "allOf";
        c = AllOfValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "oneOf";
        c = OneOfValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "not";
        c = NotValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "type";
        c = DraftV4TypeValidator.class;
        builder.addEntry(keyword, constructor(c));

        DICTIONARY = builder.freeze();
    }

    private static Constructor<? extends KeywordValidator> constructor(
        final Class<? extends KeywordValidator> c)
    {
        try {
            return c.getConstructor(JsonNode.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No appropriate constructor found", e);
        }
    }
}
