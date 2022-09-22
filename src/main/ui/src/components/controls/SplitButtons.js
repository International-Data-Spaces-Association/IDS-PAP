import * as React from "react";
import ArrowDropDownIcon from "@material-ui/icons/ArrowDropDown";
import {
  Grid,
  ClickAwayListener,
  Grow,
  Paper,
  Popper,
  MenuList,
  MenuItem,
  ButtonGroup,
  Button,
} from "@material-ui/core";
import { useStyle } from "../Style";

const options = ["Submit as ODRL", "Submit as IDS"];
const languages = ["ODRL", "IDS"];

export default function SplitButton(props) {
  const {
    valueHook,
    selectedLanguage,
    setSelectedLanguage,
    handleClick
  } = props;
  const [openLanguage, setOpenLanguage] = React.useState(false);
  const anchorRef = React.useRef(null);
  const classes = useStyle();


  const handleMenuItemClick = (event, index) => {
    const values = valueHook[0];
    const setValues = valueHook[1];
    setValues({ ...values, ["language"]: languages[index] });
    setSelectedLanguage(index);
    setOpenLanguage(false);
  };

  const handleToggle = () => {
    setOpenLanguage((prevOpen) => !prevOpen);
  };

  const handleClose = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }

    setOpenLanguage(false);
  };

  return (
    <>
      <Grid container>
        <ButtonGroup ref={anchorRef} aria-label="split button">
          <Button
            onClick={handleClick}
            variant="contained"
            color="primary"
            className={classes.saveBtn}
          >
            {options[selectedLanguage]}
          </Button>
          <Button
            variant="contained"
            color="primary"
            size="small"
            aria-controls={openLanguage ? "split-button-menu" : undefined}
            aria-expanded={openLanguage ? "true" : undefined}
            aria-label="select merge strategy"
            aria-haspopup="menu"
            onClick={handleToggle}
          >
            <ArrowDropDownIcon />
          </Button>
        </ButtonGroup>
        <Popper
          sx={{
            zIndex: 1,
          }}
          open={openLanguage}
          anchorEl={anchorRef.current}
          role={undefined}
          transition
          disablePortal
        >
          {({ TransitionProps, placement }) => (
            <Grow
              {...TransitionProps}
              style={{
                transformOrigin:
                  placement === "bottom" ? "center top" : "center bottom",
              }}
            >
              <Paper>
                <ClickAwayListener onClickAway={handleClose}>
                  <MenuList id="split-button-menu" autoFocusItem>
                    {options.map((option, index) => (
                      <MenuItem
                        key={option}
                        disabled={index === 2}
                        selected={index === selectedLanguage}
                        onClick={(event) => handleMenuItemClick(event, index)}
                      >
                        {option}
                      </MenuItem>
                    ))}
                  </MenuList>
                </ClickAwayListener>
              </Paper>
            </Grow>
          )}
        </Popper>
      </Grid>
    </>
  );
}
