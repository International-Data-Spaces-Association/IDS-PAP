import React from 'react'
import {TextField} from "@material-ui/core";

export default function Input(props) {
    const {name, label, placeholder, value, error=null, onChange} = props;
    return (
        <TextField
            label={label}
            name={name}
            placeholder={placeholder}
            value={value}
            variant="outlined"
            InputLabelProps={{ shrink: true }}
            onChange={onChange}
            {...(error && {error:true, helperText:error})}
        />
    )
}
