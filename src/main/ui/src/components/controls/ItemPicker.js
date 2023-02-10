/**
 * @file This contains the input picker component 
 * @author Tom Kollmer 
 */
import React from "react";
import { TextField, MenuItem, Grid } from "@material-ui/core";
import { handleInputChange } from "../controls/Utils";

/**
 * Components for the delete data pages
 * @component
 * @param {string} name of the input field
 * @param {string} label of the input field
 * @param {object} valueHook access to the user input
 * @param {string} defaultValue that should be used
 * @param {object} ItemList that defines all the accepted values and how they should be translated
 * @param {string} overrideValue is used to override the value without changing the value object
 * @param {func} overrideOnChange overrides the onChange function 
 * @param {object} errors contains all error messages
 * @param {number} xs size of the component at small screens
 * @param {number} sm size of the component at medium screens
 * @param {number} md size of the component at large screens
 * @returns component
 */
export default function ItemPicker(props) {
  const {
    name,
    label,
    valueHook,
    defaultValue,
    ItemList,
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
  if (values[name]=== undefined) {
    values[name] = "";
  }
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
        <TextField
          select
          name={name}
          label={label}
          value={value}
          defaultValue={defaultValue}
          variant="outlined"
          onChange={(e) => {
            onChange(e, valueHook);
          }}
          InputLabelProps={{
            shrink: true,
          }}
          {...(error && { error: true, helperText: error })}
        >
          {ItemList.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.id}
            </MenuItem>
          ))}
        </TextField>
      </Grid>
    </>
  );
}
