/*
 * Copyright (c) 2011-2024 Qulice.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the Qulice.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.qulice.maven;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link PomXpathValidator} class.
 * @since 0.6
 */
final class PomXpathValidatorTest {

    /**
     * PomXpathValidator can validate pom.xml with xpath.
     * @throws Exception If something wrong happens inside
     */
    @Test
    void canValidatePomWithXpath() throws Exception {
        final MavenEnvironment env = new MavenEnvironmentMocker()
            .withAsserts(
                Collections.singletonList(
                // @checkstyle LineLength (1 line)
                "/pom:project/pom:dependencies/pom:dependency[pom:artifactId='commons-io']/pom:version[.='1.2.5']/text()"
            )
        ).mock();
        final String pom = new TextOf(
            new ResourceOf("com/qulice/maven/PomXpathValidator/pom.xml")
        ).asString();
        FileUtils.write(
            new File(
                String.format(
                    "%s%spom.xml", env.basedir(), File.separator
                )
            ),
            pom,
            StandardCharsets.UTF_8
        );
        new PomXpathValidator().validate(env);
    }
}
