import React, { useState } from "react";
import {
  Grid,
  MenuItem,
  Menu,
  Button,
  Paper,
  Typography,
} from "@material-ui/core";
import Input from "./Input";
import ItemPicker from "./ItemPicker";
import Title from "./Title";
import { artifact_state_list } from "./InitialFieldListValues";
import { useStyle } from "../Style";
import Remove from "./Remove";

export default function DistributeDataComplex(props) {
  const {
    valueHook,
    errors,
    selectedComponents,
    setSelectedComponents,
    removeEnteredData,
    removeComponent,
  } = props;

  const [anchorEl, setAnchorEl] = useState(null);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const classes = useStyle();

  const showDistribute = () => {
    setSelectedComponents({
      ["show_distribute"]: !selectedComponents.show_distribute,
    });
  };
  return (
    <>
      {selectedComponents.availableComponents[0].isVisible ? (
        <>
          <Paper elevation={3} className={classes.paper}>
            <Grid container>
              <Grid item xs={12}>
                <Title label="Distribute Data" />
              </Grid>
              <Grid item xs={11}>
                <ItemPicker
                  name="artifactState"
                  defaultValue=""
                  label="Artifact State"
                  ItemList={artifact_state_list}
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>
              <Remove
                onClick={() => {
                  removeComponent("distributeData", "distribute");
                  removeEnteredData(["artifactState", "policy"]);
                }}
              />

              <Grid item xs={11}>
                <Input
                  name="policy"
                  label="Policy to be sent to the third party"
                  placeholder="e.g. http://example.com/policy/offer-policy"
                  valueHook={valueHook}
                  errors={errors}
                />
              </Grid>
            </Grid>
          </Paper>
        </>
      ) : (
        <Grid item xs={12} container justify="center">
          <Grid item xs={5}>
            <Button
              color="primary"
              aria-controls="simple-menu"
              aria-haspopup="true"
              onClick={() => {
                setSelectedComponents({
                  prefix: "distributeData",
                  order: [],
                  availableComponents: [
                    {
                      id: "distribute",
                      name: "Distribute Data",
                      isVisible: true,
                    },
                  ],
                });
              }}
              className={classes.addBtn}
              id="Add Component"
            >
              Add Restriction to Distribute Data
            </Button>
          </Grid>
        </Grid>
      )}
    </>
  );
}
