import React from "react";
import { TextField, MenuItem } from "@material-ui/core";

export default function ItemPicker(props) {
  const {
    name,
    label,
    defaultValue,
    ItemList,
    error = null,
    onChange,
  } = props;
  return (
    <TextField
      select
      label={label}
      name={name}
      defaultValue={defaultValue}
      variant="outlined"
      onChange={onChange}
      InputLabelProps={{
        shrink: true,
      }}
      {...(error && {error:true, helperText:error})}
    >
      {ItemList.map((option) => (
        <MenuItem key={option.value} value={option.value}>
          {option.id}
        </MenuItem>
      ))}
    </TextField>
  );
}
