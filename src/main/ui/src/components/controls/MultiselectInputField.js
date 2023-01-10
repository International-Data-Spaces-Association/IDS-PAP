/**
 * @file This contains the multi select input field component
 * @author Tom Kollmer 
 */
import React from "react";
import { IconButton, Grid, Button } from "@material-ui/core";
import ItemPicker from "./ItemPicker";
import Input from "./Input";
import { operator_list } from "./InitialFieldListValues";
import AddIcon from "@material-ui/icons/Add";
import RemoveIcon from "@material-ui/icons/Remove";
import { useStyle } from "../Style";

/**
 * Multi select input field component
 * @component
 * @param {string} name of the input field
 * @param {string} placeholder that should be used
 * @param {string} inputType that should be used "input" or "itempicker"
 * @param {object} itemList that should be used
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @param {number} xs size of the component at small screens
 * @param {number} sm size of the component at medium screens
 * @param {number} md size of the component at large screens
 * @returns component
 */
export default function MultiSelectInputField(props) {
  const {
    name,
    placeholder,
    inputType = "input",
    itemList,
    valueHook,
    errors = null,
    xs = 11,
    sm = 11,
    md = 11,
  } = props;
  const [values, setValues] = valueHook;

  const classes = useStyle();
  const numberPattern = /\d+/g;
  const handleInputChange = (e, valueHook) => {
    const e_name = e.target.name;
    const id = e_name.match(numberPattern);
    var items = values;
    if (e_name.includes("op")) {
      items[name + "_op"] = e.target.value;
    } else if (e_name.includes("input")) {
      items[name + "_input"][id] = e.target.value;
    }
    setValues({ ...items });
  };

  const handleAddItem = (e) => {
    var items = values;
    items[name + "_input"].push("");
    setValues({ ...items });
  };
  const handleRemoveItem = (index) => {
    const items = values;
    if (index !== -1) {
      items[name + "_input"].splice(index, 1);
      items[name + "_op"] = ""
      setValues({ ...items });
    }
    setValues({ ...items });
  };

  if (valueHook[0][name + "_input"].length === 1) {
    return (
      <Grid item container xs={xs} sm={sm} md={md} spacing={2}>
        {inputType === "input" && (
          <Input
            name={name + "_input_0"}
            label={""}
            placeholder={placeholder}
            valueHook={valueHook}
            overrideOnChange={handleInputChange}
            overrideValue={values[name + "_input"][0]}
            errors = {errors}
            xs={11}
            sm={11}
            md={11}
          />
        )}
        {inputType === "itempicker" && (
          <ItemPicker
            name={name + "_input_0"}
            xs={11}
            sm={11}
            md={11}
            label={""}
            ItemList={itemList}
            overrideOnChange={handleInputChange}
            overrideValue={values[name + "_input"][0]}
            valueHook={[values[name+"_input"], setValues]}
            errors = {errors}
          />
        )}

        <Grid item container xs={1} justifyContent="center" spacing={1}>
          <IconButton
            aria-label="add"
            id={name+"_add"}
            color="primary"
            className={classes.formBtn}
            onClick={() => {
              handleAddItem(0);
            }}
          >
            <AddIcon />
          </IconButton>
        </Grid>
      </Grid>
    );
  } else if (valueHook[0][name + "_input"].length > 1) {
    const components = (
      <Grid item xs={xs} sm={sm} md={md}>
        {valueHook[0][name + "_input"].map((data, id) => {
          return (
            <Grid item container xs={12} spacing={1}>
              {id === 0 && (
                <>
                  {inputType === "input" && (
                    <Input
                      name={name + "_input_" + id}
                      label={""}
                      placeholder={placeholder}
                      valueHook={valueHook}
                      errors = {errors}
                      overrideOnChange={handleInputChange}
                      overrideValue={values[name + "_input"][0]}
                      xs={9}
                      sm={9}
                      md={9}
                    />
                  )}
                  {inputType === "itempicker" && (
                    <ItemPicker
                      name={name + "_input_" + id}
                      label={""}
                      ItemList={itemList}
                      valueHook={valueHook}
                      errors = {errors}
                      overrideOnChange={handleInputChange}
                      overrideValue={values[name + "_input"][0]}
                      xs={9}
                      sm={9}
                      md={9}
                    />
                  )}
                  <ItemPicker
                    name={name + "_op"}
                    label={"Operator"}
                    ItemList={operator_list}
                    valueHook={valueHook}
                    errors = {errors}
                    overrideOnChange={handleInputChange}
                    xs={2}
                    sm={2}
                    md={2}
                  />
                </>
              )}
              {id > 0 && (
                <>
                  {inputType === "input" && (
                    <Input
                      name={name + "_input_" + id}
                      label={""}
                      placeholder={placeholder}
                      valueHook={valueHook}
                      errors = {errors}
                      overrideOnChange={handleInputChange}
                      overrideValue={data}
                      xs={11}
                      sm={11}
                      md={11}
                    />
                  )}
                  {inputType === "itempicker" && (
                    <ItemPicker
                      name={name+ "_input_" + id}
                      label={""}
                      ItemList={itemList}
                      valueHook={valueHook}
                      errors = {errors}
                      overrideOnChange={handleInputChange}
                      overrideValue={data}
                      xs={11}
                      sm={11}
                      md={11}
                    />
                  )}
                </>
              )}
              <Grid item container xs={1} justifyContent="center" spacing={1}>
                <IconButton
                  aria-label="remove"
                  color="secondary"
                  id={name+"_add"}
                  className={"btnAdd_" + id}
                  onClick={() => {
                    handleRemoveItem(id);
                  }}
                >
                  <RemoveIcon />
                </IconButton>
              </Grid>
              {id + 1 === valueHook[0][name + "_input"].length && (
                <Grid item container xs={12} justifyContent="center" spacing={1}>
                  <Button
                    color="primary"
                    aria-controls="simple-menu"
                    aria-haspopup="true"
                    id="Add Component"
                    onClick={() => {
                      handleAddItem(valueHook[0][name + "_input"].length);
                    }}
                  >
                    Add {name}
                  </Button>
                </Grid>
              )}
            </Grid>
          );
        })}
      </Grid>
    );

    return components;
  }
}
