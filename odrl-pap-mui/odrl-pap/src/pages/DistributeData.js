import React, { useState } from "react";
import { Grid, Menu, MenuItem, Button } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import ShareIcon from "@material-ui/icons/Share";
import { useStyle } from "../components/Style";
import Input from "../components/controls/Input";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import {OdrlPolicy} from '../components/backend/OdrlPolicy';
import Submit from "../components/backend/Submit";
import Remove from "../components/controls/Remove";

const selected_components = {
  encoding: false,
  policy:false,
};

export default function DistributeData() {
  const classes = useStyle();
  const [values, setValues] = useState(OdrlPolicy);
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const [selectedComponents] = useState(
    selected_components
  );
  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const resetStates = () => {
    for (var key in selectedComponents) {
      if (selectedComponents.hasOwnProperty(key)) {
        selectedComponents[key] = false;
      }
    }
    setValues({...values,
      "encoding": "",
      "policy":"",});
  };
  const handleSelectedClose = (e) => {
    selectedComponents[e.target.id] = true;
    setAnchorEl(null);
  };

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
    if (selectedComponents.encoding || selectedComponents.policy) {
      Submit(
        "/policy/DistributePolicyForm",
        values,
        selectedComponents,
        setErrors,
        history,
        e
      ); 
    }
  };
  return (
    <div className={classes.page}>
      <Form onSubmit={handleSubmit}>
        {Object.values(selectedComponents).every((x) => x === false) ? (
          <PageHeader
            title="This policy allows a specified IDS data consumer to distribute your data"
            icon={<ShareIcon />}
          />
        ) : null}
        {selectedComponents.policy ? (
          <PageHeader
            title="This policy allows a specified IDS data consumer to distribute your data only if the data is encoded (compressed or encrypted)."
            icon={<ShareIcon />}
          />
        ) : null}
        {selectedComponents.encoding ? (
          <PageHeader
            title="This policy allows a specified IDS data consumer to distribute your data and requests to send the data usage control policy to the third party as well."
            icon={<ShareIcon />}
          />
        ) : null}
        <Grid container spacing={3}>
          <IdentifyPolicy
            values={values}
            handleInputChange={handleInputChange}
            errors={errors}
          />

          {selectedComponents.encoding ? (
            <>
              <Grid item xs={11}>
                <Input
                  name="encoding"
                  label="Encoding*"
                  value={values.encoding}
                  placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                  onChange={handleInputChange}
                  error={errors.encoding}
                />
              </Grid>
              <Grid item xs={1}>
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}

          {selectedComponents.policy ? (
            <>
              <Grid item xs={11}>
                <Input
                  name="policy"
                  label="Policy to be sent to the third party*"
                  value={values.policy}
                  placeholder="e.g. https://wikidata.org/wiki/Q136218 (ZIP)"
                  onChange={handleInputChange}
                  error={errors.policy}
                />
              </Grid>
              <Grid item xs={1}>
                <Remove onClick={resetStates} />
              </Grid>
            </>
          ) : null}
          {Object.values(selectedComponents).every((x) => x === false)  ? (
            <Grid item xs={12} container justify="center">
              <Grid item xs={2}>
                {" "}
                <Button
                  color="primary"
                  aria-controls="simple-menu"
                  aria-haspopup="true"
                  onClick={handleClick}
                  className={classes.addBtn}
                >
                  Add Component
                </Button>
              </Grid>
              <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
              >
                <MenuItem onClick={handleSelectedClose} id="encoding">
                  Encoding
                </MenuItem>
                <MenuItem onClick={handleSelectedClose} id="policy">
                  Policy
                </MenuItem>
              </Menu>
            </Grid>
          ) : null}

          <Grid item xs={2}>
            {selectedComponents.encoding || selectedComponents.policy ? (
            <>
            <Button
              variant="contained"
              color="primary"
              className={classes.saveBtn}
              type="submit"
            >
              Save
            </Button>
            </>
            ) : null}
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
