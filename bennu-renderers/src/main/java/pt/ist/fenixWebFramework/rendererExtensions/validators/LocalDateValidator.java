/**
 * Copyright © 2008 Instituto Superior Técnico
 *
 * This file is part of Bennu Renderers Framework.
 *
 * Bennu Renderers Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bennu Renderers Framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Bennu Renderers Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixWebFramework.rendererExtensions.validators;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.rendererExtensions.DateTimeInputRenderer.DateTimeConverter;
import pt.ist.fenixWebFramework.renderers.components.HtmlSimpleValueComponent;
import pt.ist.fenixWebFramework.renderers.validators.HtmlChainValidator;
import pt.ist.fenixWebFramework.renderers.validators.HtmlValidator;

public class LocalDateValidator extends HtmlValidator {

    private boolean required;
    private LocalDate calculatedDate;

    public LocalDateValidator() {
        super();
        setKey(true);
    }

    public LocalDateValidator(HtmlChainValidator htmlChainValidator) {
        super(htmlChainValidator);
        setKey(true);
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public void performValidation() {
        HtmlSimpleValueComponent component = (HtmlSimpleValueComponent) getComponent();

        String value = component.getValue();

        if (value == null || value.length() == 0) {
            setMessage("renderers.validator.dateTime.required");
            setValid(!isRequired());
        } else {
            if (value.equals(DateTimeConverter.INVALID)) {
                setMessage("renderers.validator.dateTime.invalid");
                setValid(false);
            } else if (value.equals(DateTimeConverter.INCOMPLETE)) {
                setMessage("renderers.validator.dateTime.incomplete");
                setValid(false);
            } else {
                String[] dateParts = value.split("/");

                if (dateParts.length == 3) {
                    try {

                        int day = Integer.valueOf(dateParts[0]);
                        int month = Integer.valueOf(dateParts[1]);
                        int year = Integer.valueOf(dateParts[2]);
                        calculatedDate = new LocalDate(year, month, day);

                    } catch (NumberFormatException e) {
                        setMessage("renderers.validator.dateTime.notNumbers");
                        setValid(false);
                    } catch (IllegalArgumentException e) {
                        setMessage("renderers.validator.dateTime.invalid");
                        setValid(false);
                    }
                } else {
                    setMessage("renderers.validator.dateTime.invalid");
                    setValid(false);
                }
            }
        }
    }

    protected LocalDate getCalculatedDate() {
        if (isValid()) {
            return calculatedDate;
        } else {
            return null;
        }
    }

    private boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

}
