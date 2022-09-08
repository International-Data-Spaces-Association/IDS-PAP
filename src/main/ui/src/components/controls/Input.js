/**
 * @file This contains the input component 
 * @author Tom Kollmer 
 */

import React from "react";
import { TextField, Grid } from "@material-ui/core";
import { handleInputChange } from "../controls/Utils";

/**
 * Components for the delete data pages
 * @component
 * @param {string} name of the input field
 * @param {string} label of the input field
 * @param {string} placeholder that should be used
 * @param {object} valueHook access to the user input
 * @param {string} overrideValue is used to override the value without changing the value object
 * @param {func} overrideOnChange overrides the onChange function 
 * @param {func} removeEnteredData is called to remove entered data
 * @param {object} errors contains all error messages
 * @param {number} xs size of the component at small screens
 * @param {number} sm size of the component at medium screens
 * @param {number} md size of the component at large screens
 * @returns component
 */
export default function Input(props) {
  const {
    name,
    label,
    placeholder,
    valueHook,
    overrideValue = null,
    overrideOnChange = null,
    errors = null,
    xs = 11,
    sm = 11,
    md = 11,
  } = props;
  const values = valueHook[0];
  var value = values[name];
  var error = "";
  var onChange = handleInputChange;
  if (value === undefined) {
    value = "";
  }
  if (overrideValue !== null) {
    value= overrideValue;
  }
  if (errors !== null && errors[name] !== undefined) {
    error = errors[name];
  }
  if (overrideOnChange !== null) {
    onChange = overrideOnChange;
  }

  return (
    <Grid item xs={xs} sm={sm} md={md}>
      <TextField
        name={name}
        label={label}
        placeholder={placeholder}
        value={value}
        variant="outlined"
        InputLabelProps={{ shrink: true }}
        fullWidth
        onChange={(e) => {
          onChange(e, valueHook);
        }}
        {...(error && { error: true, helperText: error })}
      />
    </Grid>
  );
}
