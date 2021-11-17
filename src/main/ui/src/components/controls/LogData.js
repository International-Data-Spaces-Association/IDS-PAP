import React from "react";
import { Grid } from "@material-ui/core";
import { log_level_list } from "./InitialFieldListValues";
import Input from "./Input";
import ItemPicker from "./ItemPicker";
import Title from "./Title";

export default function LogData(props) {
  const {handleInputChange, values, errors, xs=12, sm=12, md=12, type=""} = props;
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
      <Grid container>
            <ItemPicker
              label="Log Level"
              name= {type + "logLevel"}
              defaultValue=""
              ItemList={log_level_list}
              onChange={handleInputChange}
              error={errors[type + "logLevel"]}
            />
          </Grid>

          <Grid container>
            <Input
             label="System Device"
              name={type + "systemDevice"}
              value={values[type + "systemDevice"]}
              placeholder="e.g. http://example.com/system:ESN-database"
              onChange={handleInputChange}
              error={errors[type + "systemDevice"]}
            />
          </Grid>
      </Grid>
    </>
  );
}