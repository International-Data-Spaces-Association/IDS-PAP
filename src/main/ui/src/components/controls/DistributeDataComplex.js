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
  const { values, setValues, errors, handleInputChange, removeEnteredData } =
    props;

  const [anchorEl, setAnchorEl] = useState(null);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const classes = useStyle();

  const selected_components = {
    show_distribute: false,
  };
  const [selectedComponents, setSelectedComponents] =
    useState(selected_components);

  const showDistribute = () => {
    setSelectedComponents({
      ["show_distribute"]: !selectedComponents.show_distribute,
    });
  };

  const resetStates = (e) => {
    setSelectedComponents({
      ["show_distribute"]: false,
    });
    removeEnteredData(["artifactState", "policy"]);
  };
  return (
    <>
        {selectedComponents.show_distribute ? (
          <>
                <Paper elevation={3} className={classes.paper}>
            <Grid container >
              <Grid item xs={12}>
                <Title label="Distribute Data" />
              </Grid>
              <Grid item xs={11}>
                <ItemPicker
                  name="artifactState"
                  defaultValue=""
                  label="Artifact State"
                  ItemList={artifact_state_list}
                  onChange={handleInputChange}
                  error={errors.artifactState}
                />
              </Grid>
              <Remove onClick={resetStates} />

              <Grid item xs={11}>
                <Input
                  name="policy"
                  label="Policy to be sent to the third party"
                  value={values.policy}
                  placeholder="e.g. http://example.com/policy/offer-policy"
                  onChange={handleInputChange}
                  error={errors.policy}
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
                onClick={showDistribute}
                className={classes.addBtn}
                id="Add Component"
              >
                Distribute Data
              </Button>
            </Grid>
          </Grid>
        )}
    </>
  );
}
