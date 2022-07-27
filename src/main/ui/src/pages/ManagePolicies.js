import React from "react";
import { Grid } from "@material-ui/core";
import PageHeader from "../components/PageHeader";
import NotificationsActiveIcon from "@material-ui/icons/NotificationsActive";
import { useStyle } from "../components/Style";
import { useHistory } from "react-router-dom";
import Form from "../components/controls/Form";
import MaterialTable, { Icons } from "material-table";
import { forwardRef } from "react";
import AddBox from "@material-ui/icons/AddBox";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import Check from "@material-ui/icons/Check";
import ChevronLeft from "@material-ui/icons/ChevronLeft";
import ChevronRight from "@material-ui/icons/ChevronRight";
import Clear from "@material-ui/icons/Clear";
import DeleteOutline from "@material-ui/icons/DeleteOutline";
import Edit from "@material-ui/icons/Edit";
import FilterList from "@material-ui/icons/FilterList";
import FirstPage from "@material-ui/icons/FirstPage";
import LastPage from "@material-ui/icons/LastPage";
import Remove from "@material-ui/icons/Remove";
import SaveAlt from "@material-ui/icons/SaveAlt";
import Search from "@material-ui/icons/Search";
import ViewColumn from "@material-ui/icons/ViewColumn";
import DeleteIcon from "@material-ui/icons/Delete";
import GetAppIcon from "@material-ui/icons/GetApp";

import {
  getAllPolicies,
  exportPolicy,
  deletePolicy,
  editPolicy,
  viewPolicy,
} from "../components/backend/Database";

const tableIcons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
  DetailPanel: forwardRef((props, ref) => (
    <ChevronRight {...props} ref={ref} />
  )),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  PreviousPage: forwardRef((props, ref) => (
    <ChevronLeft {...props} ref={ref} />
  )),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
  SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />),
};

export default function LogAccess() {
  const classes = useStyle();
  const history = useHistory();
  const columns = [
    { title: "Name", field: "name" },
    { title: "PolicyType", field: "policyType" },
    { title: "Description", field: "comment" },
  ];

  const [data, setData] = React.useState([]);
  //getAllPolicies(setData)
  React.useEffect(() => getAllPolicies(setData), []);
  return (
    <div className={classes.page}>
      <Form>
        <PageHeader
          title="Download or Delete Policies"
          icon={<NotificationsActiveIcon />}
        />
        <Grid container>
          <Grid item xs={12}>
            <MaterialTable
              icons={tableIcons}
              title=""
              columns={columns}
              data={data}
              actions={[
                {
                  icon: () => <DeleteIcon color="primary" />,
                  tooltip: "Delete",
                  onClick: (event, data) =>
                    deletePolicy(data.id, data, setData),
                },
                {
                  icon: () => <GetAppIcon color="primary" />,
                  tooltip: "Export",
                  onClick: (event, data) =>
                    exportPolicy(
                      data.id,
                      `policy_${data.policyID}.json`,
                      "text/plain"
                    ),
                },
                {
                  icon: () => <Edit color="primary" />,
                  tooltip: "Edit",
                  onClick: (event, data) => editPolicy(data.id, history),
                },
                /*{
                    icon: () => <Search color="primary"/>,
                    tooltip: "View",
                    onClick: (event, data) =>viewPolicy(data.id, history),
                  },*/
              ]}
              options={{
                actionsColumnIndex: -1,
                exportButton: false,
                headerStyle: {
                  fontWeight: "bold",
                },
              }}
            />
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
