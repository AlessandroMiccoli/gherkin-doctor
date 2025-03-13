/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.amiccoli.gherkindoctor.exception;

/**
 * Exception thrown when there is a misconfiguration in the application.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message) {
        super(buildMessage(message));
    }

    public ConfigurationException(String message, Throwable cause) {
        super(buildMessage(message), cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    private static String buildMessage(String message) {
        if (message.endsWith("..")) {
            message = message.substring(0, message.length() - 1);
        }
        return message;
    }
}
