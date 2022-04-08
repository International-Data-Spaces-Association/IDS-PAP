import React from "react";
import { Grid } from "@material-ui/core";
import { log_level_list } from "./InitialFieldListValues";
import Input from "./Input";
import ItemPicker from "./ItemPicker";

export default function LogData(props) {
  const {valueHook, errors, xs=12, sm=12, md=12, prefix=""} = props;
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
      <Grid container>
            <ItemPicker
              label="Log Level"
              name= {prefix + "logLevel"}
              defaultValue=""
              ItemList={log_level_list}
              valueHook={valueHook}
              errors={errors}
            />
          </Grid>

          <Grid container>
            <Input
             label="System Device"
              name={prefix + "systemDevice"}
              placeholder="e.g. http://example.com/ids/system/ESN-database"
              valueHook={valueHook}
              errors={errors}
            />
          </Grid>
      </Grid>
    </>
  );
}